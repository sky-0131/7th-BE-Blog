package com.example.blog7th.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequest {

    @Getter
    @RequiredArgsConstructor
    @Schema(description = "신고 사유 항목")
    public enum ReportReason {
        SPAM("스팸/영리적 홍보"),
        ABUSE("욕설/비하 발언"),
        INAPPROPRIATE("부적절한 콘텐츠"),
        SPAM_COMMENT("도배성 댓글"),
        BLANK("기타");

        private final String description;
    }
    @NotNull(message = "신고 사유는 필수 선택 항목입니다.")
    @Schema(
            description = "신고 사유",
            example = "SPAM",
            allowableValues = {"SPAM", "ABUSE", "INAPPROPRIATE", "SPAM_COMMENT", "BLANK"}
    )
    private ReportReason reason;
}
