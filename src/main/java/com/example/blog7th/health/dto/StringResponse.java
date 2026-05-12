package com.example.blog7th.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@AllArgsConstructor
@Schema(description = "문자열 조작 응답 DTO")

public class StringResponse {

    @Schema(description = "첫 번째 결과 문자열", example = "Hello")
    @JsonProperty("string_one")
    private String stringOne;

    @Schema(description = "두 번째 결과 문자열", example = "World")
    @JsonProperty("string_two")
    private String stringTwo;
}
