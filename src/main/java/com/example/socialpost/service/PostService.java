package com.example.socialpost.service;

import com.example.socialpost.domain.Post;
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

    public List<Post> getGroupFeed(Long groupId){
        return postJpaRepo.findByGroupId(groupId);
    }
    public Post createPost(Post param){
        Post newItem = Post.builder().content(param.getContent())
                .groupId(param.getGroupId()).build();
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
        List<Post> all = postJpaRepo.findAll();
        return all;
    }
    //게시글에 첨부된 사진, 파일, 영상의 경우 어떤 식으로 저장할지 생각하고 다시 구현해볼것

    /*
    * 좋아요 기능의 경우 테이블을 보통 따로 빼는 식으로 구현 하는듯
    * 게시글 일련번호 / 회원 일련번호 / 좋아요 여부  이런 식으로 테이블 하나 따로 필요
    * 다른 방식이 있나 고민해볼것
    * */

}
