package com.nganha.nganha.repository;

import com.nganha.nganha.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Lấy danh sách comment theo post ID, sắp xếp theo thời gian tạo.
     */
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    /**
     * Lấy danh sách reply theo comment cha.
     */
    List<Comment> findByParentCommentIdOrderByCreatedAtAsc(Long parentCommentId);

    @Modifying
    @Query("UPDATE Comment c SET c.votes = c.votes + :voteChange WHERE c.id = :commentId")
    void updateVoteCount(@Param("commentId") Long commentId, @Param("voteChange") int voteChange);
}
