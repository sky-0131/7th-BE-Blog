package com.example.blog7th.report.controller;

import com.example.blog7th.report.dto.ReportRequest;
import com.example.blog7th.report.dto.ReportResponse;
import com.example.blog7th.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report API", description = "게시글 신고 관련 API")
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "게시글 신고", description = "인증된 사용자가 특정 게시글을 신고합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신고 접수 성공"),
            @ApiResponse(responseCode = "404", description = "신고 대상 게시글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 신고 요청입니다.")
    })
    @PostMapping
    public ResponseEntity<ReportResponse> createReport(
            @RequestParam Long userId,   //  유저 아이디 입력
            @RequestParam Long postId, //  신고 대상(포스트) 아이디 입력
            @RequestBody ReportRequest request) {

        // 서비스 호출 후 결과 반환
        return ResponseEntity.ok(reportService.createReport(request, userId, postId));
    }
}
