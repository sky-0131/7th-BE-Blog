package com.example.blog7th.post.controller;

import com.example.blog7th.post.dto.*;
import com.example.blog7th.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Tag(name = "Post API", description = "게시글 작성, 조회, 수정, 삭제 및 숨김 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @Operation(summary = "게시글 작성", description = "인증된 사용자가 새로운 게시글을 작성합니다.")
    @PostMapping
    public Long createPost(@RequestBody @Valid PostRequest requestDto, @RequestParam Long userId) {
        return postService.createPost(requestDto, userId);
    }

    // 게시글 전체 목록 조회 (페이징)
    @Operation(summary = "게시글 전체 목록 조회", description = "페이징 처리된 게시글 목록을 조회합니다.")
    @GetMapping
    public Page<PostListResponse> getPostList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getPostList(pageable);
    }

    // 게시글 상세 조회
    @Operation(summary = "게시글 상세 조회", description = "특정 ID의 게시글 상세 정보를 조회합니다.")
    @GetMapping("/{postId}")
    public PostResponse getPostDetail(@PathVariable Long postId) {
        return postService.getPostDetail(postId);
    }

    // 게시글 수정
    @Operation(summary = "게시글 수정", description = "본인이 작성한 게시글을 수정합니다.")
    @PutMapping("/{postId}")
    public void updatePost(@Valid
            @PathVariable Long postId,
            @RequestBody PostRequest requestDto,
            @RequestParam Long userId) {
        postService.updatePost(postId, requestDto, userId);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "본인이 작성한 게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.deletePost(postId, userId);
    }

    // 게시물 숨기기
    @Operation(summary = "게시물 숨기기", description = "비밀번호 확인 후 본인의 게시물을 숨김 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "숨김 성공"),
            @ApiResponse(responseCode = "403", description = "본인의 게시물만 숨길 수 있습니다."),
            @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않거나 요청이 누락되었습니다.")
    })
    @PatchMapping("/{postId}/hide")
    public ResponseEntity<PostHideResponse> hidePost(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody(required = false) PostHideRequest request
    ) {
        PostHideResponse response = postService.hidePost(postId, userId, request);
        return ResponseEntity.ok(response);
    }
}
