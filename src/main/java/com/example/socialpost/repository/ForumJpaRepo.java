package com.example.socialpost.repository;

import com.example.socialpost.domain.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ForumJpaRepo extends JpaRepository<Forum, Long> {
    @Transactional
    @Modifying
    @Query("delete from Forum f where f.forumId in :ids")
    void deleteAllByIds(@Param("ids") List<Long> ids);
}
