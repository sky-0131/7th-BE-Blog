package com.example.blog7th.health.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Health Check API", description = "서버 상태 확인용 API")
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @Operation(summary = "서버 상태 확인", description = "서버가 정상적으로 구동 중인지 확인합니다.")
    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
