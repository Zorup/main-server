package com.example.socialpost.repository;

import com.example.socialpost.domain.Like;
import com.example.socialpost.domain.LikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface LikeJpaRepo extends JpaRepository<Like, LikePK> {
    @Transactional
    @Query("select l from Like l where l.compositeKey.user.userId = :userId")
    Like findByUserId(@Param("userId") Long userId);
}
