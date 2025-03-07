package com.nganha.nganha.dto.vote;

import com.nganha.nganha.enums.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VotePostDto(
        @NotNull(message = "Post ID is required")
        Long postId,

        @NotNull(message = "Vote type is required")
        VoteType type
) {}

