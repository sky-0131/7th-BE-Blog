package com.example.blog7th.like.controller;

import com.example.blog7th.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like API", description = "게시글 및 댓글 좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요 API
    @Operation(summary = "게시글 좋아요 토글", description = "인증된 사용자가 게시글의 좋아요 상태를 변경합니다. (좋아요/취소)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 상태 변경 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없습니다.")
    })
    @PostMapping("/post/{postId}")
    public ResponseEntity<String> togglePostLike(
            @PathVariable Long postId,
            @RequestParam Long userId) {

        likeService.togglePostLike(userId, postId);
        return ResponseEntity.ok("게시글 좋아요 상태가 변경되었습니다.");
    }

    // 댓글 좋아요 API
    @Operation(summary = "댓글 좋아요 토글", description = "인증된 사용자가 댓글의 좋아요 상태를 변경합니다. (좋아요/취소)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 상태 변경 성공"),
            @ApiResponse(responseCode = "404", description = "해당 댓글을 찾을 수 없습니다.")
    })
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestParam Long userId) {

        likeService.toggleCommentLike(userId, commentId);
        return ResponseEntity.ok("댓글 좋아요 상태가 변경되었습니다.");
    }
}
