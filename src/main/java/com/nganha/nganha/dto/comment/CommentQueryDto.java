package com.nganha.nganha.dto.comment;

import com.nganha.nganha.enums.CommentSortType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record CommentQueryDto(
        @NotNull
        Long postId,

        CommentSortType sort,
        @NotNull
        int limit,

        @NotNull
        int offset
) {
    public Pageable getPageable() {
        return PageRequest.of(this.offset / this.limit, this.limit);
    }
}
