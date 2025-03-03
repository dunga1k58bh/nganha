package com.nganha.nganha.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank(message = "Username cannot be empty")
        @Pattern(regexp = "^[a-z0-9_]+$", message = "Username name can only contain lowercase letters, numbers, and underscores")
        @Size(max = 50, message = "Username cannot exceed 50 characters")
        String username,

        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        String email
) {}