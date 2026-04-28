package com.example.blog7th.comment.domain;

import com.example.blog7th.global.domain.BaseEntity;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    //
    // N:1 관계 (작성자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // N:1 관계 (게시글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //대댓글, 자기 참조
    // 부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 자식 댓글들
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();


    @Builder
    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.isPinned = false;
    }

    //
    @Column(name = "is_pinned", nullable = false)
    private boolean isPinned = false;

    public boolean isPinned() {
        return isPinned;
    }
    public void pin() {
        this.isPinned = true;
    }
    public void unpin() {
        this.isPinned = false;
    }
}