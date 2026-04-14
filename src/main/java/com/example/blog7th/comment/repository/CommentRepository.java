package com.example.blog7th.comment.repository;

import com.example.blog7th.comment.domain.Comment;
import com.example.blog7th.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByIsPinnedDescCreatedAtAsc(Post post);
}
