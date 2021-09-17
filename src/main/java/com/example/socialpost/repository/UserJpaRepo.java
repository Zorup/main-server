package com.example.socialpost.repository;

import com.example.socialpost.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);

    @Query("select u.userId as userId, u.pushToken as pushToken from User u where u.userId in :userIds")
    List<UserProjection> findPushTokenByUserIdIn(@Param("userIds")Long[] userIds);
}
