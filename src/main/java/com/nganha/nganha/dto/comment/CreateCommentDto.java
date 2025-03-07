package com.nganha.nganha.dto.comment;

import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentDto(

        /**
         * The ID of the post to which this comment belongs.
         * Cannot be null.
         */
        @NotNull(message = "Post ID is required")
        Long postId,

        /**
         * The ID of the parent comment (if any).
         * If this is a top-level comment, it can be null.
         */
        Long parentCommentId,

        /**
         * The content of the comment.
         * Cannot be null or empty.
         * Maximum length: 10,000 characters.
         */
        @NotBlank(message = "Content must not be empty")
        @Size(max = 10_000, message = "Content must not exceed 10,000 characters")
        String content
) {
    public Comment toEntity(Post post, User author, Comment parentComment) {
        return Comment.builder()
                .post(post)
                .author(author)
                .parentComment(parentComment)
                .content(content)
                .build();
    }
}
