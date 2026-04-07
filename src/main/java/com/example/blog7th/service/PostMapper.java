package com.example.blog7th.service; // 또는 .mapper

import com.example.blog7th.domain.post.Post;
import com.example.blog7th.dto.PostResponse;
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
}