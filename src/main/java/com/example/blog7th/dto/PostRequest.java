package com.example.blog7th.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    // 📍 이 부분을 추가하세요!
    @NotBlank(message = "공개 여부는 필수입니다.")
    private String status;
}