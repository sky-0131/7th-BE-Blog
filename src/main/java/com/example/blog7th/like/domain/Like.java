package com.example.blog7th.like.domain;

import com.example.blog7th.global.domain.BaseEntity;
import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes") // 'like'는 SQL 예약어일 수 있어 'likes'로 명명 권장
public class Like extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public Like(User user, Post post, Comment comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }
    @PrePersist
    public void validate() {
        // 하나만 처리
        if ((post == null && comment == null) || (post != null && comment != null)) {
            throw new IllegalStateException("좋아요는 게시글 또는 댓글 중 하나에만 할당되어야 합니다.");
        }
    }
}