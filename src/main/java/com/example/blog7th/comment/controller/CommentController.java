package com.example.blog7th.comment.controller;

import com.example.blog7th.comment.dto.CommentPinResponse;
import com.example.blog7th.comment.dto.CommentRequest;
import com.example.blog7th.comment.dto.CommentResponse;
import com.example.blog7th.comment.service.CommentService;
import com.example.blog7th.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment API", description = "댓글 작성, 조회 및 고정 관련 API")
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     *POST
     */
    @Operation(summary = "댓글 작성", description = "인증된 사용자가 특정 게시글에 댓글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.")
    })
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
    @Operation(summary = "댓글 목록 조회", description = "특정 게시글에 달린 모든 댓글을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {

        // 특정 게시글(postId)에 달린 댓글 리스트 조회
        List<CommentResponse> responses = commentService.getComments(postId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "댓글 고정", description = "게시글 작성자가 댓글을 상단에 고정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 고정 성공"),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    // 댓글 고정
    @PatchMapping("/{commentId}/pin")
    public ResponseEntity<CommentPinResponse> pinComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user) {

        CommentPinResponse response = commentService.pinComment(postId, commentId, user.getId());

        return ResponseEntity.ok(response);
    }

}
