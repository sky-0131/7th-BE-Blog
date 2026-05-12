package com.example.blog7th.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Schema(description = "문자열 반복 요청 DTO")
public class StringRequest {
    @NotBlank (message = "반복할 문자열을 반드시 입력해주세요. 공백으로 답할 수 없습니다.")
    @Schema(description = "반복할 문자열", example = "Hello")
    private String value;
}
