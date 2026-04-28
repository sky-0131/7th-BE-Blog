package com.example.blog7th.comment.controller;

import com.example.blog7th.comment.dto.CommentRequest;
import com.example.blog7th.comment.dto.CommentResponse;
import com.example.blog7th.comment.service.CommentService;
import com.example.blog7th.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal User user) {

        // 서비스의 createComment 메서드를 호출하여 댓글 저장 후 반환
        CommentResponse response = commentService.createComment(postId, request, user);
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
}
