package com.nganha.nganha.dto.group;

import com.nganha.nganha.enums.GroupRole;
import jakarta.validation.constraints.NotNull;

public record UpdateUserGroupDto(
        @NotNull(message = "Group ID cannot be null")
        Long groupId,

        @NotNull(message = "User ID cannot be null")
        Long userId,

        @NotNull(message = "Role cannot be null")
        GroupRole role
) {}