package com.nganha.nganha.resolver;

import com.nganha.nganha.dto.comment.CommentDto;
import com.nganha.nganha.dto.comment.CreateCommentDto;
import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.security.CurrentUser;
import com.nganha.nganha.service.CommentService;
import com.nganha.nganha.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class CommentResolver {

    private final CommentService commentService;
    private final PostService postService;

    /**
     * List all comments for admin debugging purposes.
     * @return List of CommentDto
     */
    @QueryMapping
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments()
                .stream()
                .map(CommentDto::fromEntity) // Convert entity to DTO
                .toList();
    }

    /**
     * Fetch a single comment by its ID.
     * @param id the ID of the comment
     * @return CommentDto if found, otherwise throws an exception
     */
    @QueryMapping
    public CommentDto comment(@Argument Long id) {
        return commentService.getCommentById(id)
                .map(CommentDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + id));
    }


    @MutationMapping
    public Comment createComment(@Valid @Argument("input")CreateCommentDto input, @CurrentUser User user){
        Post post = postService.getPostById(input.postId()).orElse(null);
        if (post == null){
            throw new IllegalArgumentException("Invalid post");
        }

        Comment parent = commentService.getCommentById(input.parentCommentId()).orElse(null);
        Comment comment = input.toEntity(post, user, parent);
        return commentService.createComment(comment);
    }

    @MutationMapping
    public boolean deleteComment(@Argument Long commentId, @CurrentUser User user){
        Comment comment = commentService.getCommentById(commentId).orElse(null);
        if (comment == null){
            throw new IllegalArgumentException("Invalid comment");
        }

        return commentService.deleteComment(comment, user);
    }
}
