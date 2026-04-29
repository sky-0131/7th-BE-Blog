package com.example.blog7th.comment.repository;

import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List; // 반드시 java.util.List를 임포트해야 합니다.

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
    List<Comment> findAllByPostOrderByIsPinnedDescCreatedAtAsc(Post post);
}