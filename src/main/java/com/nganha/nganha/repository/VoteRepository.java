package com.nganha.nganha.repository;

import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.entity.Vote;
import com.nganha.nganha.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Tìm vote của user trên một post.
     */
    Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);
    Optional<Vote> findByUserAndPost(User user, Post post);

    /**
     * Tìm vote của user trên một comment.
     */
    Optional<Vote> findByUserIdAndCommentId(Long userId, Long commentId);
    Optional<Vote> findByUserAndComment(User user, Comment comment);

    /**
     * Đếm số lượng upvote của một post.
     */
    long countByPostIdAndType(Long postId, VoteType type);

    /**
     * Đếm số lượng upvote của một comment.
     */
    long countByCommentIdAndType(Long commentId, VoteType type);
}
