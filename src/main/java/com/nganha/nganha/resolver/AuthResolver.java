package com.nganha.nganha.resolver;

import com.nganha.nganha.dto.auth.RegisterDto;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.security.CurrentUser;
import com.nganha.nganha.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@PreAuthorize("isAuthenticated()")
public class AuthResolver {
    private final AuthService authService;

    public AuthResolver(AuthService authService) {
        this.authService = authService;
    }

    @MutationMapping
    @PreAuthorize("permitAll()")
    public Map<String, String> login(@Argument String username, @Argument String password) {
        return authService.login(username, password);
    }

    @MutationMapping
    @PreAuthorize("permitAll()")
    public Map<String, String> register(@Valid @Argument("input") RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @MutationMapping
    @PreAuthorize("permitAll()")
    public Map<String, String> refreshToken(@Argument String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @MutationMapping
    @PreAuthorize("permitAll()")
    public Map<String, String> logout(@Argument String refreshToken) {
        return authService.logout(refreshToken);
    }

    @QueryMapping
    public String secureData(@CurrentUser User user) {
        return "This is secured data!" + user.getUsername();
    }
}