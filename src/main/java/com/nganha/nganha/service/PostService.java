package com.nganha.nganha.service;

import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserGroupService userGroupService;

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getPostsByAuthor(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public List<Post> getPostsByGroup(Long groupId) {
        return postRepository.findByGroupId(groupId);
    }

    @Transactional
    public Post createPost(Post post) {
        if (post.getAuthor() == null) {
            throw new IllegalArgumentException("Author cannot be null.");
        }

        if (post.getGroup() != null && !userGroupService.isMember(post.getAuthor(), post.getGroup())) {
            throw new SecurityException("User is not a member of this group.");
        }

        return postRepository.save(post);
    }


    @Transactional
    public Post updatePost(Post post) {
        if (!postRepository.existsById(post.getId())) {
            throw new EntityNotFoundException("Post not found with id: " + post.getId());
        }
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Post post, User currentUser) {
        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new SecurityException("You do not have permission to delete this post.");
        }
        postRepository.deleteById(post.getId());
    }
}