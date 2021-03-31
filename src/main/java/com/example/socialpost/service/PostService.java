package com.example.socialpost.service;

import com.example.socialpost.domain.Post;
import com.example.socialpost.repository.ForumJpaRepo;
import com.example.socialpost.repository.PostJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepo postJpaRepo;
    private final ForumJpaRepo forumJpaRepo;

    public Post createPost(Post.PostRequest param){
        Post newItem = Post.builder().content(param.getContent())
                .groupId(param.getGroupId()).forum(forumJpaRepo.findById(param.getForumId()).get()).build();
        //param에서 파일 or 영상 or 사진 있는 경우 추가 user 추가
        return postJpaRepo.save(newItem);
    }

    public Post modifyContents(Post param){
        Post target = postJpaRepo.findById(param.getPostId()).get();
        target.setContent(param.getContent());
        return postJpaRepo.save(target);
    }
    
    public void deletePost(Long postId){
        postJpaRepo.deleteById(postId);
    }
    
    public List<Post> getAllPosts(){
        return postJpaRepo.findAll();
    }

    public List<Post> getForumPosts(Long forumId){
        return postJpaRepo.findByForumForumId(forumId);
    }
}
