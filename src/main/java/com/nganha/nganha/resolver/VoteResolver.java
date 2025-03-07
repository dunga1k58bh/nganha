package com.nganha.nganha.resolver;

import com.nganha.nganha.dto.vote.VoteCommentDto;
import com.nganha.nganha.dto.vote.VotePostDto;
import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.entity.Vote;
import com.nganha.nganha.security.CurrentUser;
import com.nganha.nganha.service.CommentService;
import com.nganha.nganha.service.PostService;
import com.nganha.nganha.service.VoteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class VoteResolver {

    private final VoteService voteService;
    private final PostService postService;
    private final CommentService commentService;

    /**
     * Handle voting for a Post.
     */
    @MutationMapping
    @Transactional
    public boolean votePost(@Valid @Argument("input") VotePostDto input, @CurrentUser User user) {
        Post post = postService.getPostById(input.postId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + input.postId()));

        voteService.votePost(post, user, input.type());
        return true;
    }

    /**
     * Handles voting for a comment.
     */
    @MutationMapping
    @Transactional
    public boolean voteComment(@Valid @Argument("input") VoteCommentDto input, @CurrentUser User user) {
        Comment comment = commentService.getCommentById(input.commentId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + input.commentId()));

        voteService.voteComment(comment, user, input.type());
        return true;
    }


}
