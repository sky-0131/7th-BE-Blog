package com.example.blog7th.service;

import com.example.blog7th.domain.*;
import com.example.blog7th.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용으로 설정 (성능 최적화)
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 게시글 좋아요 토글 (누르면 생성, 다시 누르면 취소)
     */
    @Transactional
    public void togglePostLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        // 1. 이미 좋아요를 눌렀는지 확인 (누가, 어떤 포스트에)
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            // 2. 이미 있다면? 삭제 (좋아요 취소)
            likeRepository.delete(existingLike.get());
        } else {
            // 3. 없다면? 생성 (좋아요 1개 제한 자동 해결)
            Like like = Like.builder()
                    .user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
        }
    }

    /**
     * 댓글 좋아요 토글
     */
    @Transactional
    public void toggleCommentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        likeRepository.findByUserAndComment(user, comment)
                .ifPresentOrElse(
                        likeRepository::delete, // 존재하면 삭제
                        () -> likeRepository.save(Like.builder().user(user).comment(comment).build()) // 없으면 저장
                );
    }
}