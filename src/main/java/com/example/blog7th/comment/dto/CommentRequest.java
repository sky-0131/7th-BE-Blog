package com.example.blog7th.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 작성 및 수정 요청 DTO")
public class CommentRequest {
    @Schema(description = "댓글 본문 내용", example = "재밌어요.")
    private String content; // 댓글 본문
}
