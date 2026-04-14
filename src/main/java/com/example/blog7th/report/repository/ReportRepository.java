package com.example.blog7th.report.repository;

import com.example.blog7th.report.domain.Report;
import com.example.blog7th.user.domain.User;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // 게시물을 이미 신고했는지 (중복 방지용)
    boolean existsByReporterAndPost(User reporter, Post post);

    // 댓글을 이미 신고했는지 (중복 방지용)
    boolean existsByReporterAndComment(User reporter, Comment comment);
}
