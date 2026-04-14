package com.example.blog7th.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentPinRequest {
    private Long postId; // 검증용 게시물 ID
}
