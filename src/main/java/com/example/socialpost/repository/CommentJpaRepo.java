package com.example.socialpost.repository;

import com.example.socialpost.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPost_PostId(Long postId);
}
