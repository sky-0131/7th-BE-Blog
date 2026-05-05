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
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // [수정 1] 파라미터는 그대로 3개(request, userId, postId)를 유지합니다.
    public ReportResponse createReport(ReportRequest request, Long userId, Long postId) {

        User reporter = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("신고하려는 유저가 존재하지 않습니다."));

        String reason = (request.getReason() == null || request.getReason().isBlank())
                ? "사유 없음" : request.getReason();

        // [수정 2] request.getType()에 빨간 줄이 뜬다면, 아예 "POST"라고 직접 박아버리세요.
        // 어차피 게시글 신고 전용이라면 이게 가장 확실합니다.
        if (true) { // 게시글 신고 전용이므로 if문을 통과시키거나 "POST".equals("POST") 정도로 두세요.
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

            if (post.getUser().getId().equals(reporter.getId())) {
                throw new IllegalStateException("본인이 작성한 게시글은 신고할 수 없습니다.");
            }
            if (reportRepository.existsByReporterAndPost(reporter, post)) {
                throw new IllegalStateException("이미 이 게시글을 신고하셨습니다.");
            }

            // [수정 3] 현재 신고 저장 전이므로 count가 3일 때 숨겨야 이번 신고로 4개가 됩니다.
            long reportCount = reportRepository.countByPost(post);
            if (reportCount >= 3) {
                post.hide();
            }

            Report report = Report.builder()
                    .reason(reason)
                    .reporter(reporter)
                    .post(post)
                    .build();

            return ReportResponse.success(reportRepository.save(report).getId());
        }

        throw new IllegalArgumentException("올바르지 않은 신고입니다.");
    }
}
