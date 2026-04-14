package com.example.blog7th.report.controller;

import com.example.blog7th.report.dto.ReportRequest;
import com.example.blog7th.report.dto.ReportResponse;
import com.example.blog7th.report.service.ReportService;
import com.example.blog7th.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(
            @RequestBody ReportRequest request,
            @AuthenticationPrincipal User user) {

        // 서비스 호출 후 결과 반환
        ReportResponse response = reportService.createReport(request, user);

        return ResponseEntity.ok(response);
    }
}