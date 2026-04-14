package com.example.blog7th.report.domain;

import com.example.blog7th.global.domain.BaseEntity;
import com.example.blog7th.user.domain.User;
import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "report", uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_reporter_comment",
                columnNames = {"reporter_id", "comment_id"}
        ),
        @UniqueConstraint(
                name = "unique_reporter_post",
                columnNames = {"reporter_id", "post_id"}
        )
})
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(nullable = false, length = 500)
    private String reason; // 신고 사유

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    // 신고한 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    // 신고 대상 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 신고 대상 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static Report createReport(String reason, User reporter, Post post, Comment comment) {
        if (post == null && comment == null) {
            throw new IllegalArgumentException("신고 대상(게시글 또는 댓글)이 지정되어야 합니다.");
        }
        return Report.builder()
                .reason(reason)
                .reporter(reporter)
                .post(post)
                .comment(comment)
                .build();
    }
}