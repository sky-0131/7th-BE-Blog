package com.example.blog7th.comment.controller;

import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.comment.dto.CommentRequest;
import com.example.blog7th.comment.dto.CommentResponse;
import com.example.blog7th.comment.repository.CommentRepository;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.post.repository.PostRepository;
import com.example.blog7th.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(Long postId, CommentRequest request, User user) {
        // 게시글 존재 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // DTO -> Entity 변환
        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        // 저장 및 반환
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.from(savedComment);
    }
}
