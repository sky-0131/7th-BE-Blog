package com.example.blog7th.comment.dto;

import com.example.blog7th.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private String content;
    private String nickname; // 작성자 닉네임
    private boolean isPinned;
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
