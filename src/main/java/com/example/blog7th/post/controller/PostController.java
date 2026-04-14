package com.example.blog7th.post.controller;

import com.example.blog7th.post.dto.*;
import com.example.blog7th.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public Long createPost(@RequestBody @Valid PostRequest requestDto, @RequestParam Long userId) {
        return postService.createPost(requestDto, userId);
    }

    // 게시글 전체 목록 조회 (페이징)
    @GetMapping
    public Page<PostListResponse> getPostList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getPostList(pageable);
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    public PostResponse getPostDetail(@PathVariable Long postId) {
        return postService.getPostDetail(postId);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public void updatePost(@Valid
            @PathVariable Long postId,
            @RequestBody PostRequest requestDto,
            @RequestParam Long userId) {
        postService.updatePost(postId, requestDto, userId);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.deletePost(postId, userId);
    }

    // 게시물 숨기기
    @PatchMapping("/{postId}/hide")
    public ResponseEntity<PostHideResponse> hidePost(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody(required = false) PostHideRequest request
    ) {
        PostHideResponse response = postService.hidePost(postId, userId);
        return ResponseEntity.ok(response);
    }
}
