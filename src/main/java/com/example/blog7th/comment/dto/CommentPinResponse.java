package com.example.blog7th.comment.dto;

import com.example.blog7th.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentPinResponse {
    private final Long commentId;
    private final String content;
    private final boolean isPinned;
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
