package com.example.blog7th.post.mapper; // 또는 .mapper

import com.example.blog7th.post.domain.Post;
import com.example.blog7th.post.dto.PostHideResponse;
import com.example.blog7th.post.dto.PostResponse;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    // Entity -> Response DTO 변환
    public PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .userId(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments().stream()
                        .map(comment -> PostResponse.CommentDto.builder()
                                .commentId(comment.getId())
                                .nickname(comment.getUser().getNickname())
                                .content(comment.getContent())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public PostHideResponse toHideResponse(Post post) {
        return PostHideResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .status(post.getStatus())
                .message("게시물이 성공적으로 숨김 처리되었습니다.")
                .build();
    }
}