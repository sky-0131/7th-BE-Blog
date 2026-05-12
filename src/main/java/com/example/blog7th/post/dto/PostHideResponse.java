package com.example.blog7th.post.dto;

import com.example.blog7th.post.domain.Post; // 임포트 추가
import com.example.blog7th.post.domain.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 생성자 접근 제한
@Schema(description = "게시글 숨김 처리 결과 응답 DTO")
public class PostHideResponse {

    @Schema(description = "숨김 처리된 게시글 ID", example = "1")
    private final Long postId;

    @Schema(description = "게시글 제목", example = "비공개로 전환된 게시글입니다.")
    private final String title;

    @Schema(description = "변경 후 게시글 상태", example = "HIDDEN")
    private final PostStatus status;

    @Schema(description = "결과 메시지", example = "게시글이 성공적으로 숨겨졌습니다.")
    private final String message;

    public static PostHideResponse of(Post post, String message) {
        return PostHideResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .status(post.getStatus())
                .message(message)
                .build();
    }
}

