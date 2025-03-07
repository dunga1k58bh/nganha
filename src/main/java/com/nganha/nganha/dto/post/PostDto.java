package com.nganha.nganha.dto.post;

import com.nganha.nganha.dto.user.UserDto;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.enums.PostVisibility;
import com.nganha.nganha.enums.PostType;
import com.nganha.nganha.enums.VoteType;

import java.time.Instant;
import java.util.List;

public record PostDto(
        Long id,
        UserDto author,
        Long groupId,
        String title,
        String content,
        List<String> mediaUrls,
        String linkUrl,
        PostVisibility visibility,
        PostType type,
        int votes,
        VoteType userVote,
        int commentCount,
        boolean isEdited,
        boolean isPinned,
        boolean isLocked,
        Instant createdAt,
        Instant updatedAt
) {
    public static PostDto fromEntity(Post post, VoteType userVote) {
        return new PostDto(
                post.getId(),
                UserDto.fromEntity(post.getAuthor()),
                post.getGroupId(),
                post.getTitle(),
                post.getContent(),
                post.getMediaUrls(),
                post.getLinkUrl(),
                post.getVisibility(),
                post.getType(),
                post.getVotes(),
                userVote,
                post.getCommentCount(),
                post.isEdited(),
                post.isPinned(),
                post.isLocked(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
