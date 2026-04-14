package com.example.blog7th.like.repository;

import com.example.blog7th.like.domain.Like;
import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.post.domain.Post;
import com.example.blog7th.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    //포스트 좋아요 확인
    Optional<Like> findByUserAndPost(User user, Post post);

    //댓글 좋아요 확인
    Optional<Like> findByUserAndComment(User user, Comment comment);
}
