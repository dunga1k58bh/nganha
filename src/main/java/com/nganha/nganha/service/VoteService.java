package com.nganha.nganha.service;

import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Post;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.entity.Vote;
import com.nganha.nganha.enums.VoteType;
import com.nganha.nganha.repository.CommentRepository;
import com.nganha.nganha.repository.PostRepository;
import com.nganha.nganha.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    private Vote createVotePost(Post post, User user, VoteType type){
        Vote vote = Vote.builder()
                .post(post)
                .user(user)
                .type(type)
                .build();
        return voteRepository.save(vote);
    }


    private Vote createVoteComment(Comment comment, User user, VoteType type){
        Vote vote = Vote.builder()
                .comment(comment)
                .user(user)
                .type(type)
                .build();
        return voteRepository.save(vote);
    }


    @Transactional
    public Vote votePost(Post post, User user, VoteType type) {
        Optional<Vote> optionalVote = voteRepository.findByUserAndPost(user, post);
        Vote vote;
        int voteChange = 0;

        if (optionalVote.isPresent()) {
            vote = optionalVote.get();
            if (!vote.getType().equals(type)) {
                voteChange = (type == VoteType.UPVOTE) ? 2 : -2;
                vote.setType(type);
            }
        } else {
            vote = createVotePost(post, user, type);
            voteChange = (type == VoteType.UPVOTE) ? 1 : -1;
        }

        if (voteChange != 0) {
            postRepository.updateVoteCount(post.getId(), voteChange);
        }

        return voteRepository.save(vote);
    }


    public Vote voteComment(Comment comment, User user, VoteType type) {
        Optional<Vote> optionalVote = voteRepository.findByUserAndComment(user, comment);
        Vote vote;
        int voteChange = 0;

        if (optionalVote.isPresent()) {
            vote = optionalVote.get();
            if (!vote.getType().equals(type)) {
                voteChange = (type == VoteType.UPVOTE) ? 2 : -2;
                vote.setType(type);
            }
        } else {
            vote = createVoteComment(comment, user, type);
            voteChange = (type == VoteType.UPVOTE) ? 1 : -1;
        }

        if (voteChange != 0) {
            commentRepository.updateVoteCount(comment.getId(), voteChange);
        }

        return voteRepository.save(vote);
    }
}
