package com.example.blog7th.comment.dto;

import com.example.blog7th.comment.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "댓글 정보 응답 DTO")
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = "댓글 본문 내용", example = "재밌어요")
    private String content;

    @Schema(description = "작성자 닉네임", example = "sky0131")
    private String nickname;

    @Schema(description = "상단 고정 여부", example = "false")
    private boolean isPinned;

    @Schema(description = "작성 일시", example = "2024-03-20T14:30:00")
    private LocalDateTime createdAt;

    // Entity를 DTO로 변환해주는 편의 메서드
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getNickname()) // User 엔티티에서 가져옴
                .isPinned(comment.isPinned())
                .createdAt(comment.getCreatedAt()) // BaseEntity가 있다면 사용
                .build();
    }
}
