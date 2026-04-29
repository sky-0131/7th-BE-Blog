package com.example.blog7th.post.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PostListResponse {
    // 게시글 정보
    private Long postId;
    private String title;
    private String nickname;
}
