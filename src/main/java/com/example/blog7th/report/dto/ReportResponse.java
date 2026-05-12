package com.example.blog7th.report.dto;

import com.example.blog7th.report.domain.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 신고 요청 DTO")
public class ReportResponse {

    @Schema(description = "생성된 신고 고유 번호", example = "101")
    private Long reportId;

    @Schema(description = "처리 결과 메시지", example = "신고가 성공적으로 접수되었습니다.")
    private String message;

    @Schema(description = "신고 처리 상태", example = "PENDING")
    private ReportStatus status;

    @Schema(description = "신고로 인한 게시글 숨김 처리 여부", example = "true")
    private boolean isHidden;

    @Schema(description = "해당 게시글의 누적 신고 횟수", example = "5")
    private long currentCount;

    public static ReportResponse success(Long id) {
        return ReportResponse.builder()
                .reportId(id)
                .message("신고가 성공적으로 접수되었습니다.")
                .status(ReportStatus.PENDING)
                .build();
    }
}
