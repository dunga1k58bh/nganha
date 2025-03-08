package com.nganha.nganha.dto.comment;

import com.nganha.nganha.dto.user.UserDto;
import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.enums.VoteType;

import java.time.Instant;

public record CommentDto(
        Long id,
        Long postId,
        Long parentCommentId, // Store only ID
        UserDto author,
        String content,
        VoteType userVote,
        int votes,
        int replyCount,
        boolean isEdited,
        Instant createdAt,
        Instant updatedAt
) {
    public static CommentDto fromEntity(Comment comment, VoteType userVote) {
        return new CommentDto(
                comment.getId(),
                comment.getPostId(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null, // Only store ID
                UserDto.fromEntity(comment.getAuthor()),
                comment.getContent(),
                userVote,
                comment.getVotes(),
                comment.getReplyCount(),
                comment.isEdited(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }


    public static CommentDto fromEntity(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getPostId(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null, // Only store ID
                UserDto.fromEntity(comment.getAuthor()),
                comment.getContent(),
                null,
                comment.getVotes(),
                comment.getReplyCount(),
                comment.isEdited(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
