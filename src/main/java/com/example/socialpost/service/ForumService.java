package com.example.socialpost.service;


import com.example.socialpost.domain.Forum;
import com.example.socialpost.repository.ForumJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ForumService {
    private final ForumJpaRepo forumJpaRepo;
    public Forum addForum(String forumName){ // 차후 parameter로 그룹 아이디 받기
        Forum newForum = Forum.builder().forumName(forumName).build();
        return forumJpaRepo.save(newForum);
    }
}
