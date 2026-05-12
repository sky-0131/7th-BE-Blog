package com.example.blog7th.health.controller;

import com.example.blog7th.health.dto.StringRequest;
import com.example.blog7th.health.dto.StringResponse;
import com.example.blog7th.health.service.StringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "String API", description = "문자열 조작 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StringController {

    private final StringService stringService;

    @Operation(summary = "문자열 반복", description = "입력받은 문자열을 특정 횟수만큼 반복하여 반환합니다.")
    @PostMapping("strings/repeat")
    public StringResponse repeat(@Valid @RequestBody StringRequest request) {

        return stringService.repeatString(request.getValue());
    }
}
