package com.example.blog7th.post.domain;

import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.global.domain.BaseEntity;
import com.example.blog7th.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 1000)
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Column(name = "view_count")
    private int viewCount = 0;

    //
    // N:1 관계 (작성자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String content, String thumbnail, PostStatus status, User user) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.status = status;
        this.user = user;
    }

    public void update(String title, String content, PostStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    // 게시물 숨김 처리
    public void hide() {
        if (this.status == PostStatus.HIDDEN) {
            throw new IllegalStateException("이미 숨김 처리된 게시물입니다.");
        }
        this.status = PostStatus.HIDDEN;
    }

    // 작성자 일치 여부 확인
    public boolean isOwner(Long userId) {
        if (userId == null || this.user == null) {
            return false;
        }
        return userId.equals(this.user.getId());
    }

    public void unpinAllComments() {
        this.comments.stream()
                .filter(Comment::isPinned) // 고정된 댓글들만 필터링
                .forEach(Comment::unpin);  // 모두 고정 해제 호출
    }

}