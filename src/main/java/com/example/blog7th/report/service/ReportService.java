package com.example.blog7th.report.service;

import com.example.blog7th.comment.repository.CommentRepository;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.post.repository.PostRepository;
import com.example.blog7th.report.domain.Report;
import com.example.blog7th.report.dto.ReportRequest;
import com.example.blog7th.report.dto.ReportResponse;
import com.example.blog7th.report.repository.ReportRepository;
import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ReportResponse createReport(ReportRequest request, Long userId, Long postId) {

        User reporter = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("신고하려는 유저가 존재하지 않습니다."));

        ReportRequest.ReportReason reason = (request.getReason() == null)
                ? ReportRequest.ReportReason.BLANK : request.getReason();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 자기 글 신고 불가 검증
        if (post.getUser().getId().equals(reporter.getId())) {
            throw new IllegalStateException("본인이 작성한 게시글은 신고할 수 없습니다.");
        }

        // 중복 신고 확인
        if (reportRepository.existsByReporterAndPost(reporter, post)) {
            throw new IllegalStateException("이미 이 게시글을 신고하셨습니다.");
        }

        Report report = Report.builder()
                .reason(reason.getDescription())
                .reporter(reporter)
                .post(post)
                .build();

        Report savedReport = reportRepository.save(report);

        // 6. 신고 누적에 따른 자동 숨김 처리 (기존 로직 유지)
        long reportCount = reportRepository.countByPost(post);
        if (reportCount >= 3) {
            post.hide();
        }

        return ReportResponse.success(savedReport.getId());
    }
}
