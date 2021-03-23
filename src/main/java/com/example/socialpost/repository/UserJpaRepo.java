package com.example.socialpost.repository;

import com.example.socialpost.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {
}
