package com.nganha.nganha.service;

import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.repository.CommentRepository;
import com.nganha.nganha.service.acl.CommentAclService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentAclService commentAclService;
    private final CommentRepository commentRepository;
    private final GroupService groupService;

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
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

        return commentRepository.save(comment);
    }


    @Transactional
    public boolean deleteComment(Comment comment, User user){
        return true;
    }
}
