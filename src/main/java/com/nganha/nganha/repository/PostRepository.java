package com.nganha.nganha.repository;

import com.nganha.nganha.entity.Post;
import com.nganha.nganha.enums.PostSortType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

    List<Post> findByAuthorId(Long authorId);

    List<Post> findByGroupId(Long groupId);

    List<Post> findByAuthorIdAndGroupId(Long authorId, Long groupId);

    @Modifying
    @Query("UPDATE Post p SET p.votes = p.votes + :voteChange WHERE p.id = :postId")
    void updateVoteCount(@Param("postId") Long postId, @Param("voteChange") int voteChange);

    @Query("""
        SELECT p, v.type
        FROM Post p
        LEFT JOIN Vote v ON p.id = v.post.id AND v.user.id = :userId
        WHERE (:groupId IS NULL OR p.group.id = :groupId)
          AND (:authorId IS NULL OR p.author.id = :authorId)
        ORDER BY
            CASE WHEN :sort = 'NEW' THEN p.createdAt END DESC,
            CASE WHEN :sort = 'TOP' THEN p.votes END DESC
    """)
    List<Object[]> findPosts(
            @Param("groupId") Long groupId,
            @Param("authorId") Long authorId,
            @Param("sort") String sort,
            @Param("userId") Long userId,
            Pageable pageable
    );
}
