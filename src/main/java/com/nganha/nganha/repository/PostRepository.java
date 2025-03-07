package com.nganha.nganha.repository;

import com.nganha.nganha.entity.Post;
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
}
