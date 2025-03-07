package com.nganha.nganha.dto.comment;

import com.nganha.nganha.dto.user.UserDto;
import com.nganha.nganha.entity.Comment;

import java.time.Instant;

public record CommentDto(
        Long id,
        Long postId,
        Long parentCommentId, // Store only ID
        UserDto author,
        String content,
        int votes,
        int replyCount,
        boolean isEdited,
        Instant createdAt,
        Instant updatedAt
) {
    public static CommentDto fromEntity(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getPost().getId(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null, // Only store ID
                UserDto.fromEntity(comment.getAuthor()),
                comment.getContent(),
                comment.getVotes(),
                comment.getReplyCount(),
                comment.isEdited(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
