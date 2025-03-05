package com.nganha.nganha.dto.group;

import jakarta.validation.constraints.NotNull;

public record UpdateGroupConfigDto(
        @NotNull(message = "Group ID cannot be null")
        Long groupId,

        Object config // JSON config (nullable)
) {}