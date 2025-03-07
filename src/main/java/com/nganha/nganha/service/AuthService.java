package com.nganha.nganha.service;

import com.nganha.nganha.dto.auth.RegisterDto;
import com.nganha.nganha.entity.RefreshToken;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.enums.Role;
import com.nganha.nganha.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    public Map<String, String> login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = refreshTokenService.createRefreshToken(user);

            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    public Map<String, String> register(RegisterDto registerDto) {
        if (userService.doesUserExist(registerDto.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        User newUser = userService.createUser(registerDto.username(), registerDto.password(), registerDto.email(), Role.USER);
        String accessToken = jwtUtil.generateAccessToken(newUser);
        String refreshToken = refreshTokenService.createRefreshToken(newUser);

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    public Map<String, String> refreshToken(String refreshToken) {
        RefreshToken token = refreshTokenService.findByToken(refreshToken);
        if (token == null || refreshTokenService.isTokenExpired(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }

        User user = userService.getUserById(token.getUserId()).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user);
        return Map.of("accessToken", newAccessToken);
    }

    public Map<String, String> logout(String refreshToken) {
        refreshTokenService.revokeRefreshToken(refreshToken);
        return Map.of("message", "Logged out successfully");
    }

    public void authenticateUserFromToken(String jwtToken) throws Exception {
        // Validate token
        if (!jwtUtil.validateToken(jwtToken)) {
            throw new Exception("Invalid token");
        }

        // Extract user details from token
        String username = jwtUtil.extractUsername(jwtToken);

        // Load user from database
        User user = userService.loadUserByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        user.setPassword(null);

        // Create authentication token with correct authorities
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());

        // Set authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
