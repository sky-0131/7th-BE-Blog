package com.example.blog7th.controller;

import com.example.blog7th.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요 API
    @PostMapping("/post/{postId}")
    public ResponseEntity<String> togglePostLike(
            @PathVariable Long postId,
            @RequestParam Long userId) {

        likeService.togglePostLike(userId, postId);
        return ResponseEntity.ok("게시글 좋아요 상태가 변경되었습니다.");
    }

    // 댓글 좋아요 API
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestParam Long userId) {

        likeService.toggleCommentLike(userId, commentId);
        return ResponseEntity.ok("댓글 좋아요 상태가 변경되었습니다.");
    }
}
