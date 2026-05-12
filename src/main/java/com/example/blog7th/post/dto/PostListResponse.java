package com.example.blog7th.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 목록 조회용 응답 DTO")
public class PostListResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "스프링 부트로 블로그 만들기")
    private String title;

    @Schema(description = "작성자 닉네임", example = "개발하는사자")
    private String nickname;
}
