package com.nganha.nganha.dto.post;

import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.enums.PostType;
import com.nganha.nganha.enums.PostVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreatePostDto(
        Long groupId,

        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        String content,

        List<String> mediaUrls,

        @Pattern(regexp = "^(https?|ftp)://.*$", message = "Invalid URL format")
        String linkUrl,

        @NotNull(message = "Visibility is required")
        PostVisibility visibility,

        @NotNull(message = "Post type is required")
        PostType type
) {
    public Post toEntity(User author, Group group) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .mediaUrls(this.mediaUrls)
                .linkUrl(this.linkUrl)
                .visibility(this.visibility)
                .type(this.type)
                .author(author)
                .group(group)
                .build();
    }
}

