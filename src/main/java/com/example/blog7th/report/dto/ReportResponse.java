package com.example.blog7th.report.dto;

import com.example.blog7th.report.domain.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReportResponse {
    private Long reportId;      // 생성된 신고 번호
    private String message;     // "신고가 접수되었습니다." 등의 메시지
    private ReportStatus status; // 현재 상태 (PENDING 등)

    public static ReportResponse success(Long id) {
        return ReportResponse.builder()
                .reportId(id)
                .message("신고가 성공적으로 접수되었습니다.")
                .status(ReportStatus.PENDING)
                .build();
    }
}