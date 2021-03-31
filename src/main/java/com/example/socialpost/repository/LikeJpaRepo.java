package com.example.socialpost.repository;

import com.example.socialpost.domain.Like;
import com.example.socialpost.domain.LikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepo extends JpaRepository<Like, LikePK> {
}
