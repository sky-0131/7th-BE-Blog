package com.example.blog7th.comment.service;

import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.comment.dto.CommentPinResponse;
import com.example.blog7th.comment.dto.CommentRequest;
import com.example.blog7th.comment.dto.CommentResponse;
import com.example.blog7th.comment.repository.CommentRepository;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.post.repository.PostRepository;
import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse createComment(Long postId, Long userId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.from(savedComment);
    }

    public List<CommentResponse> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        List<Comment> comments = commentRepository.findAllByPostOrderByIsPinnedDescCreatedAtAsc(post);

        return comments.stream()
                .map((Comment c) -> CommentResponse.from(c))
                .collect(Collectors.toList());
    }

    // 댓글 고정
    @Transactional
    public CommentPinResponse pinComment(Long commentId, Long userId) {
        // 댓글 존재 여부 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        // 댓글이 속한 게시글 객체 가져오기
        Post post = comment.getPost();

        if (!post.isOwner(userId)) {
            throw new IllegalStateException("게시글 작성자만 댓글을 고정할 수 있습니다.");
        }

        post.unpinAllComments();

        comment.pin();
        return CommentPinResponse.from(comment, "댓글이 성공적으로 고정되었습니다.");
    }
}
