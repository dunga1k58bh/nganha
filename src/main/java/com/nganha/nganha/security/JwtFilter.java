package com.nganha.nganha.security;

import com.nganha.nganha.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public JwtFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            try {
                String jwtToken = token.substring(7);
                authService.authenticateUserFromToken(jwtToken);
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
