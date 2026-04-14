package com.example.blog7th.post.dto;

import com.example.blog7th.post.domain.Post; // 임포트 추가
import com.example.blog7th.post.domain.PostStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 생성자 접근 제한
public class PostHideResponse {
    private final Long postId;
    private final String title;
    private final PostStatus status;
    private final String message;

    public static PostHideResponse from(Post post) {
        return PostHideResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .status(post.getStatus())
                .message("게시물이 성공적으로 숨겨졌습니다.")
                .build();
    }
}