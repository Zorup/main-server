package com.example.socialpost.repository;

import com.example.socialpost.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageJpaRepo extends JpaRepository<Image, Long> {
    Optional<List<Image>> findByPost_PostId_OrderBySeq(Long postId);
    void deleteAllByPost_PostId(Long postId);
}
