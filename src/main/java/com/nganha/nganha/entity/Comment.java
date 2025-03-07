package com.nganha.nganha.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The post to which this comment belongs.
     * A comment must belong to a post.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "post_id", insertable = false, updatable = false)
    private Long postId; // Store post ID directly for reference

    /**
     * The user who created this comment.
     * A comment must have an author.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "author_id", insertable = false, updatable = false)
    private Long authorId; // Store author ID directly for reference

    /**
     * The parent comment (if any).
     * If this is a reply, it has a value; if it is a top-level comment, it is null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Column(name = "parent_comment_id", insertable = false, updatable = false)
    private Long parentCommentId; // Store parent comment ID directly for reference

    /**
     * The content of the comment.
     * Cannot be null.
     */
    @Lob
    @Column(nullable = false)
    private String content;

    /**
     * The number of votes for this comment.
     * Default value is 0.
     */
    @Column(nullable = false)
    @Builder.Default
    private int votes = 0;

    /**
     * The number of replies to this comment.
     * Default value is 0.
     */
    @Column(nullable = false)
    @Builder.Default
    private int replyCount = 0;

    /**
     * Indicates whether the comment has been edited.
     * Default is false.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean isEdited = false;

    /**
     * Checks if the given user is the author of the comment.
     * Uses authorId to avoid unnecessary database fetch.
     */
    public boolean isAuthoredBy(User user) {
        return user != null && user.getId().equals(this.authorId);
    }
}