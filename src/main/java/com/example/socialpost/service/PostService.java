package com.example.socialpost.service;

import com.example.socialpost.domain.Comment;
import com.example.socialpost.domain.Post;
import com.example.socialpost.repository.CommentJpaRepo;
import com.example.socialpost.repository.ForumJpaRepo;
import com.example.socialpost.repository.PostJpaRepo;
import com.example.socialpost.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepo postJpaRepo;
    private final ForumJpaRepo forumJpaRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final UserJpaRepo userJpaRepo;
    public Post createPost(Post.PostRequest param){
        Post newItem = Post.builder().content(param.getContent())
                .groupId(param.getGroupId()).forum(forumJpaRepo.findById(param.getForumId()).get())
                .user(userJpaRepo.findById(param.getUserId()).get()).build();
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

    public List<Post.PostResponse> getPostLists(Long forumId){
        log.info("log :: postview start...");
        List<Post.PostResponse> prList = new ArrayList<>();
        List<Post> pList = getForumPosts(forumId); //1query

        log.info("postview :: post List load success");
        if(pList.isEmpty()){
            log.info("postview :: post List is Empty");
        }
        for(Post p: pList){
            log.info("postview :: add PostResponse start..");
            Post.PostResponse pr = new Post.PostResponse(p); //2 query
            log.info("postview :: find comments start..");
            List<Comment> comments = commentJpaRepo.findByPost_PostId(p.getPostId()); //3query
            log.info("postview :: comment List load success");
            if(!comments.isEmpty()){
                pr.setComments(comments);
            }
            prList.add(pr);
        } 
        return prList;
    }
}
