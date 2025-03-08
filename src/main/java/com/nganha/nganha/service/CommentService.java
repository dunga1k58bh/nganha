package com.nganha.nganha.service;

import com.nganha.nganha.dto.comment.CommentDto;
import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.enums.CommentSortType;
import com.nganha.nganha.enums.VoteType;
import com.nganha.nganha.repository.CommentRepository;
import com.nganha.nganha.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final GroupService groupService;

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findComments(Post post, CommentSortType sortType, User currentUser, Pageable pageable) {
        if (post == null){
            throw new IllegalArgumentException("Invalid post!");
        }

        Long postId =  post.getId();
        List<Object[]> results = commentRepository.findComments(postId, sortType.name(), currentUser.getId(), pageable);
        return results.stream()
                .map(result -> {
                    Comment comment = (Comment) result[0];
                    VoteType userVote = (VoteType) result[1];
                    return CommentDto.fromEntity(comment, userVote);
                })
                .toList();
    }


    /**
     * Service to create a comment for both post & reply
     * @param comment The comment object entity
     * @return Comment
     */
    @Transactional
    public Comment createComment(Comment comment){
        if(comment.getPost() == null){
            throw new IllegalArgumentException("Invalid post");
        }

        comment = commentRepository.save(comment);

        //Increase comment count by 1
        postRepository.updateCommentCount(comment.getPost().getId(), 1);

        return comment;
    }


    @Transactional
    public boolean deleteComment(Comment comment, User user){
        commentRepository.delete(comment);

        //Decrease comment count by 1
        commentRepository.updateVoteCount(comment.getPost().getId(), -1);

        return true;
    }
}
