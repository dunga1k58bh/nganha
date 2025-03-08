package com.nganha.nganha.repository;

import com.nganha.nganha.entity.Comment;
import org.springframework.data.domain.Pageable;
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

    @Query("""
        SELECT c, v.type
        FROM Comment c
        LEFT JOIN Vote v ON c.id = v.comment.id AND v.user.id = :userId
        WHERE c.post.id = :postId
        ORDER BY
            CASE WHEN :sort = 'NEW' THEN c.createdAt END DESC,
            CASE WHEN :sort = 'TOP' THEN c.votes END DESC
    """)
    List<Object[]> findComments(
            @Param("postId") Long postId,
            @Param("sort") String sort,
            @Param("userId") Long userId,
            Pageable pageable
    );

}
