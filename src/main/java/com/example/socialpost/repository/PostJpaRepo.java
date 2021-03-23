package com.example.socialpost.repository;

import com.example.socialpost.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    List<Post> findByGroupId(Long groupId);
}
