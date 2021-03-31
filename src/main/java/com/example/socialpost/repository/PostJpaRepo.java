package com.example.socialpost.repository;

import com.example.socialpost.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    List<Post> findByForumForumId(Long forumId);
}
