package com.example.socialpost.service;


import com.example.socialpost.domain.Forum;
import com.example.socialpost.repository.ForumJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ForumService {
    private final ForumJpaRepo forumJpaRepo;

    public Forum getDefaultForum(Long id){ // 일단 무조건 1번 리턴중. 후에 디폴트포럼 리턴하게 변경필요
        return forumJpaRepo.findById(id).get();
    }

    public Forum addForum(String forumName){ // 차후 parameter로 그룹 아이디 받기
        Forum newForum = Forum.builder().forumName(forumName).build();
        return forumJpaRepo.save(newForum);
    }

    public void deleteForum(List<Long> indexs){
        forumJpaRepo.deleteAllByIds(indexs);
    }
    public List<Forum> findAllForum(){
        return forumJpaRepo.findAll();
    }
}
