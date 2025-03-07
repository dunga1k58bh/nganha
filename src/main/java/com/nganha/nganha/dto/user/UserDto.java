package com.nganha.nganha.dto.user;

import com.nganha.nganha.entity.User;

public record UserDto(
        Long id,
        String username,
        String imageUrl
) {
    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getImageUrl());
    }
}