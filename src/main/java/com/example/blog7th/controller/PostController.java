package com.example.blog7th.controller;

import com.example.blog7th.dto.PostRequest;
import com.example.blog7th.dto.PostResponse;
import com.example.blog7th.dto.PostListResponse;
import com.example.blog7th.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public Long createPost(@RequestBody PostRequest requestDto, @RequestParam Long userId) {
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
    public void updatePost(
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
}
