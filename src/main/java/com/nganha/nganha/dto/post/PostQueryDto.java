package com.nganha.nganha.dto.post;

import com.nganha.nganha.enums.PostSortType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record PostQueryDto(
        /**
         * GroupId if provide
         */
        @Nullable
        Long groupId,

        /**
         * @AuthorId if provide
         */
        @Nullable
        Long authorId,

        @NotNull
        PostSortType sort,

        @NotNull
        int limit,

        @NotNull
        int offset
) {
    public Pageable getPageable(){
        return PageRequest.of(this.offset / this.limit, this.limit);
    }
}
