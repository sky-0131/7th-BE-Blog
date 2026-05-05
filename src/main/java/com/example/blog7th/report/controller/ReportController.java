package com.example.blog7th.report.controller;

import com.example.blog7th.report.dto.ReportRequest;
import com.example.blog7th.report.dto.ReportResponse;
import com.example.blog7th.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(
            @RequestParam Long userId,   //  유저 아이디 입력
            @RequestParam Long postId, //  신고 대상(포스트) 아이디 입력
            @RequestBody ReportRequest request) {

        // 서비스 호출 후 결과 반환
        return ResponseEntity.ok(reportService.createReport(request, userId, postId));
    }
}
