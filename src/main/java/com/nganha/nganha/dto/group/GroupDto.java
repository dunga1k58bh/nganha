package com.nganha.nganha.dto.group;

import com.nganha.nganha.entity.Group;

import java.time.Instant;

public record GroupDto (

        Long id,
        String name,
        String displayName,
        Object config,
        Instant createdAt,
        Instant updatedAt

){
    public static GroupDto fromEntity(Group group){
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getDisplayName(),
                group.getConfig(),
                group.getCreatedAt(),
                group.getUpdatedAt()
        );
    }
}
