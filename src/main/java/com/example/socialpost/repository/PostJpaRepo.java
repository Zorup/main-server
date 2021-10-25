package com.example.socialpost.repository;

import com.example.socialpost.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    List<Post> findByForumForumId(Long forumId);

    @Query("SELECT p FROM Post AS p WHERE p.forum.forumId = :forumId and p.postId < :oldestId")
    List<Post> findByForum_ForumId_WithPagination(@Param("forumId") Long forumId, @Param("oldestId") Long oldestId, Pageable pageable);

    @Query("SELECT p FROM Post AS p WHERE p.forum.forumId = :forumId")
    List<Post> findByForum_ForumId_WithPagination_Initial(@Param("forumId") Long forumId, Pageable pageable);
}
