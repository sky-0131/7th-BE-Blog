package com.example.blog7th.post.service;

import com.example.blog7th.post.dto.PostListResponse;
import com.example.blog7th.post.dto.PostRequest;
import com.example.blog7th.post.dto.PostResponse;
import com.example.blog7th.user.domain.User;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.user.repository.UserRepository;
import com.example.blog7th.post.repository.PostRepository;
import com.example.blog7th.post.mapper.PostMapper;
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
        // 유저 존재 여부 확인 및 객체 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER4041"));

        // 게시글 객체 생성 (중복 선언 제거 및 타입 변환 적용)
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .status(requestDto.getStatus())
                .user(user) // 📍 외래키 연결
                .build();

        // 저장 후 생성된 ID 반환
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

        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getStatus());
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