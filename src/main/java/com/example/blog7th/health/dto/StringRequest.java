package com.example.blog7th.health.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class StringRequest {
    @NotBlank (message = "반복할 문자열을 반드시 입력해주세요. 공백으로 답할 수 없습니다.")
    private String value;
}
