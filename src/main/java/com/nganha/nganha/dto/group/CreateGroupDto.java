package com.nganha.nganha.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateGroupDto(
        @NotBlank(message = "Group name cannot be empty")
        @Pattern(regexp = "^[a-z0-9_]+$", message = "Group name can only contain lowercase letters, numbers, and underscores")
        @Size(max = 50, message = "Group name cannot exceed 50 characters")
        String name,

        @NotBlank(message = "Display name cannot be empty")
        @Size(max = 100, message = "Display name cannot exceed 100 characters")
        String displayName,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        String config // JSON config (optional)
) {}
