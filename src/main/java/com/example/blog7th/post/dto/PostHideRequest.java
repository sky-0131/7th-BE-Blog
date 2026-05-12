package com.example.blog7th.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 숨김 요청 DTO (비밀번호 검증 포함)")
public class PostHideRequest {
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Schema(description = "게시글 숨김 처리를 위한 비밀번호", example = "012345")
    private String password;
}
