package com.example.blog7th.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private String nickname;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> comments; // 댓글 목록 포함

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentDto {
        private Long commentId;
        private String nickname;
        private String content;
    }
}
