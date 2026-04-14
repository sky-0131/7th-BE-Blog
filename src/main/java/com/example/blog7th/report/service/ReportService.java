package com.example.blog7th.report.service;

import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.comment.repository.CommentRepository;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.post.repository.PostRepository;
import com.example.blog7th.report.domain.Report;
import com.example.blog7th.report.dto.ReportRequest;
import com.example.blog7th.report.dto.ReportResponse;
import com.example.blog7th.report.repository.ReportRepository;
import com.example.blog7th.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ReportResponse createReport(ReportRequest request, User reporter) {
        // 1. 사유가 없거나 공백이면 "사유 없음"으로 자동 설정
        String reason = (request.getReason() == null || request.getReason().isBlank())
                ? "사유 없음" : request.getReason();

        // 2. 게시글 신고 처리
        if ("POST".equalsIgnoreCase(request.getType())) {
            Post post = postRepository.findById(request.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

            // [검증] 자기 글 신고 불가
            if (post.getUser().getId().equals(reporter.getId())) {
                throw new IllegalStateException("본인이 작성한 게시글은 신고할 수 없습니다.");
            }
            // [검증] 중복 신고 체크
            if (reportRepository.existsByReporterAndPost(reporter, post)) {
                throw new IllegalStateException("이미 이 게시글을 신고하셨습니다.");
            }

            Report report = Report.builder()
                    .reason(reason)
                    .reporter(reporter)
                    .post(post)
                    .build();

            return ReportResponse.success(reportRepository.save(report).getId());
        }

        // 3. 댓글 신고 처리
        if ("COMMENT".equalsIgnoreCase(request.getType())) {
            Comment comment = commentRepository.findById(request.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

            // [검증] 자기 댓글 신고 불가
            if (comment.getUser().getId().equals(reporter.getId())) {
                throw new IllegalStateException("본인이 작성한 댓글은 신고할 수 없습니다.");
            }
            // [검증] 중복 신고 체크
            if (reportRepository.existsByReporterAndComment(reporter, comment)) {
                throw new IllegalStateException("이미 이 댓글을 신고하셨습니다.");
            }

            Report report = Report.builder()
                    .reason(reason)
                    .reporter(reporter)
                    .comment(comment)
                    .build();

            return ReportResponse.success(reportRepository.save(report).getId());
        }

        throw new IllegalArgumentException("신고 타입(POST/COMMENT)이 올바르지 않습니다.");
    }
}

