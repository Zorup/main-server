package com.zorup.main.repository;

import com.zorup.main.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    List<Post> findByGroupId(Long groupId);
}
