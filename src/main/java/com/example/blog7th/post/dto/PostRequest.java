package com.example.blog7th.post.dto;

import com.example.blog7th.post.domain.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 작성 및 수정 요청 DTO")
public class PostRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Schema(description = "게시글 제목", example = "스프링 부트 완벽 가이드")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Schema(description = "게시글 본문 내용", example = "괜찮습니다.")
    private String content;

    //(수정) Enum은 빈 문자열이 아닌 객체이므로 @NotNull을 사용.
    @NotNull(message = "공개 여부는 필수입니다.")
    @Schema(description = "게시글 공개 상태", example = "PUBLIC", allowableValues = {"PUBLIC", "PRIVATE", "HIDDEN"})
    private PostStatus status;
}
