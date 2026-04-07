package com.example.blog7th.service;

import com.example.blog7th.domain.*;
import com.example.blog7th.domain.post.Comment;
import com.example.blog7th.domain.post.Post;
import com.example.blog7th.domain.user.User;
import com.example.blog7th.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // if-else 대신 ifPresentOrElse 사용
        likeRepository.findByUserAndPost(user, post)
                .ifPresentOrElse(
                        // 1. 이미 좋아요가 있을 때 (삭제)
                        like -> likeRepository.delete(like),

                        // 2. 없을 때 (생성 및 저장)
                        () -> {
                            Like like = Like.builder()
                                    .user(user)
                                    .post(post)
                                    .build();
                            likeRepository.save(like);
                        }
                );
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