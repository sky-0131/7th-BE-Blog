package com.example.blog7th.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequest {

    @Getter
    @RequiredArgsConstructor
    public enum ReportReason {
        SPAM("스팸/영리적 홍보"),
        ABUSE("욕설/비하 발언"),
        INAPPROPRIATE("부적절한 콘텐츠"),
        SPAM_COMMENT("도배성 댓글"),
        BLANK("기타");

        private final String description;
    }
    private ReportReason reason;
}
