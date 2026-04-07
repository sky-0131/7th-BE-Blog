package com.example.blog7th.repository;

import com.example.blog7th.domain.*;
import com.example.blog7th.domain.post.Comment;
import com.example.blog7th.domain.post.Post;
import com.example.blog7th.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    //포스트 좋아요 확인
    Optional<Like> findByUserAndPost(User user, Post post);

    //댓글 좋아요 확인
    Optional<Like> findByUserAndComment(User user, Comment comment);
}
