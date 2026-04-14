package com.example.blog7th.post.dto;

import com.example.blog7th.post.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    //(수정) Enum은 빈 문자열이 아닌 객체이므로 @NotNull을 사용.
    @NotNull(message = "공개 여부는 필수입니다.")
    private PostStatus status;
}