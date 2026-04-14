package com.example.blog7th.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequest {

    // 신고 대상의 ID
    private Long targetId;

    // 무엇을 신고하는지 구분
    private String type;

    // 사유
    private String reason;
}
