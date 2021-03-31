package com.example.socialpost.repository;

import com.example.socialpost.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumJpaRepo extends JpaRepository<Forum, Long> {
}
