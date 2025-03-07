package com.nganha.nganha.dto.vote;

import com.nganha.nganha.enums.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VoteCommentDto(
        @NotNull(message = "Comment ID is required")
        Long commentId,

        @NotNull(message = "Vote type is required")
        VoteType type
) {}

