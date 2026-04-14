package com.example.blog7th.post.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PostListResponse {
    private Long postId;
    private String title;
    private String nickname;
}
