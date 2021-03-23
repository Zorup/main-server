package com.example.socialpost.repository;

import com.example.socialpost.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepo extends JpaRepository<Comment,Long> {
}
