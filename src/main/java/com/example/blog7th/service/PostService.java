package com.example.blog7th.service;

import com.example.blog7th.dto.*;
import com.example.blog7th.domain.user.User;
import com.example.blog7th.domain.post.Post;
import com.example.blog7th.repository.UserRepository;
import com.example.blog7th.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    // 최신 목록 조회
    public Page<PostListResponse> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> PostListResponse.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .nickname(post.getUser().getNickname())
                        .build());
    }

    // 상세 조회, 댓글 포함
    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("POST4041")); // 존재하지 않는 글 예외
        //mapper
        return postMapper.toResponse(post);
    }

    // 게시글 작성
    @Transactional
    public Long createPost(PostRequest requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER4041")); // 유저가 존재하지 않음

        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        return postRepository.save(post).getId();
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, PostRequest requestDto, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("POST4041"));

        // 권한 확인
        if (!post.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("COMMON403");
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("POST4041"));

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("COMMON403");
        }

        postRepository.delete(post);
    }
}