package com.example.blog7th.comment.controller;

import com.example.blog7th.comment.dto.CommentPinResponse;
import com.example.blog7th.comment.dto.CommentRequest;
import com.example.blog7th.comment.dto.CommentResponse;
import com.example.blog7th.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     *POST
     */
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody CommentRequest request) {

        // 서비스의 createComment 메서드를 호출하여 댓글 저장 후 반환
        CommentResponse response = commentService.createComment(postId, userId, request);
        return ResponseEntity.ok(response);
    }
    /**
     * GET
     */
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {

        // 특정 게시글(postId)에 달린 댓글 리스트 조회
        List<CommentResponse> responses = commentService.getComments(postId);
        return ResponseEntity.ok(responses);
    }

    // 댓글 고정
    @PatchMapping("/{commentId}/pin")
    public ResponseEntity<CommentPinResponse> pinComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam Long userId) {

        CommentPinResponse response = commentService.pinComment(commentId, userId);

        return ResponseEntity.ok(response);
    }

}
