package com.example.blog7th.comment.dto;

import com.example.blog7th.comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "댓글 고정 응답 DTO")
public class CommentPinResponse {
    @Schema(description = "댓글 ID", example = "1")
    private final Long commentId;

    @Schema(description = "댓글 내용", example = "고정된 댓글의 내용입니다.")
    private final String content;

    @Schema(description = "고정 여부", example = "true")
    private final boolean isPinned;

    @Schema(description = "결과 메시지", example = "댓글이 상단에 고정되었습니다.")
    private final String message;

    public static CommentPinResponse from(Comment comment, String message) {
        return CommentPinResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .isPinned(comment.isPinned())
                .message(message)
                .build();
    }
}
