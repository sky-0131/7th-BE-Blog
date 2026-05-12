package com.example.blog7th.post.dto;

import com.example.blog7th.comment.dto.CommentResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 상세 정보 응답 DTO (댓글 목록 포함)")
public class PostResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "월 백 만원 파이프라인 만드는 법")
    private String title;

    @Schema(description = "게시글 본문", example = "상세한 본문 내용이 여기에 들어갑니다.")
    private String content;

    @Schema(description = "작성자 닉네임", example = "sky0131")
    private String nickname;

    @Schema(description = "작성자 고유 ID", example = "10")
    private Long userId;

    @Schema(description = "최초 작성일", example = "2024-03-20T14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "최종 수정일", example = "2024-03-21T10:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "게시글에 달린 댓글 목록")
    private List<CommentResponse> comments;

    @Getter
    @Builder
    @AllArgsConstructor
    @Schema(description = "게시글 상세용 댓글 요약 정보")
    public static class CommentDto {

        @Schema(description = "댓글 ID", example = "1")
        private Long commentId;

        @Schema(description = "댓글 작성자 닉네임", example = "sky0131")
        private String nickname;

        @Schema(description = "댓글 내용", example = "고정해주세요")
        private String content;
    }
}
