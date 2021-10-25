package com.example.socialpost.service;

import com.example.socialpost.domain.Comment;
import com.example.socialpost.domain.Image;
import com.example.socialpost.domain.Post;
import com.example.socialpost.domain.User;
import com.example.socialpost.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepo postJpaRepo;
    private final ForumJpaRepo forumJpaRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final ImageJpaRepo imageJpaRepo;
    private final UserService userService;

    public Post.PostResponse createPost(@CookieValue(value = "X-Auth-Token") Cookie cookie,
                                        Post.PostRequest param,
                                        Image.ImageRequestForm imageRequestForm) throws IOException {
        User u = userService.getInfoBytoken(cookie.getValue());
        Post newPost = Post.builder().content(param.getContent())
                .groupId(param.getGroupId()).forum(forumJpaRepo.findById(param.getForumId()).get())
                .likes(0)
                .user(u).build();

        log.info("createPost :: insert new Post");
        final Post newPostSaved = postJpaRepo.save(newPost);
        Post.PostResponse postResponse = new Post.PostResponse(newPostSaved);

        // 넘어온 이미지가 있으면 이미지 저장하고 응답객체에 추가
        if (imageRequestForm != null){
            log.info("createPost :: image data found");
            List<Image.ImageRequest> images = imageRequestForm.getImages();

            List<Image> imageList = images.stream()
                    .map(img -> Image.of(img).post(newPostSaved).build())
                    .collect(Collectors.toList());

            log.info("createPost :: insert new Images");
            imageList = imageJpaRepo.saveAll(imageList);

            postResponse.setImages(
                    imageList.stream().map(Image.ImageResponse::of).collect(Collectors.toList())
            );
        }

        //param에서 파일 or 영상 있는 경우 추가 user 추가

        return postResponse;
    }

    public Post modifyContents(Post param){
        Post target = postJpaRepo.findById(param.getPostId()).get();
        target.setContent(param.getContent());
        return postJpaRepo.save(target);
    }
    
    public void deletePost(Long postId){
        postJpaRepo.deleteById(postId);
    }

    public List<Post> getForumPosts(Long forumId, Long oldestId, Integer pageSize){
        // forumId 가지는 post들 중 postId가 oldestId보다 오래된(작은) 것들만 pageSize만큼 가져옵니다.

        int defaultPageSize = 5;

        Sort sort = Sort.by("postId").descending();
        PageRequest pageable;
        if (pageSize != null)
            pageable = PageRequest.of(0, pageSize, sort);
        else
            pageable = PageRequest.of(0, defaultPageSize, sort);

        if (oldestId != null)
            return postJpaRepo.findByForum_ForumId_WithPagination(forumId, oldestId, pageable);
        else
            return postJpaRepo.findByForum_ForumId_WithPagination_Initial(forumId, pageable);
    }

    public List<Post.PostResponse> getPostLists(Long forumId, Long oldestId, Integer pageSize){
        log.info("log :: postview start...");
        List<Post.PostResponse> prList = new ArrayList<>();
        List<Post> pList = getForumPosts(forumId, oldestId, pageSize); //1query

        log.info("postview :: post List load success");
        if(pList.isEmpty()){
            log.info("postview :: post List is Empty");
        }
        for(Post p: pList){
            prList.add(makeResponse(p));
        }

        return prList;
    }

    public Post.PostResponse getPost(Long postId){
        Post post = postJpaRepo.findById(postId).get();
        return makeResponse(post);
    }

    private Post.PostResponse makeResponse(Post post){
        Post.PostResponse pr = new Post.PostResponse(post);

        log.info("load comment list");
        List<Comment> comments = commentJpaRepo.findByPost_PostId(post.getPostId());
        if(!comments.isEmpty()){
            List<Comment.CommentResponse> crList = new ArrayList<>();
            for(Comment c: comments){
                Comment.CommentResponse cr = new Comment.CommentResponse(c);
                crList.add(cr);
            }
            pr.setComments(crList);
        }

        log.info("load image list");
        imageJpaRepo.findByPost_PostId_OrderBySeq(post.getPostId())
                .ifPresent(images -> {
                    List<Image.ImageResponse> irList = images.stream()
                            .map(Image.ImageResponse::of).collect(Collectors.toList());
                    pr.setImages(irList);
                });

        return pr;
    }


    public List<Post> getAllPosts(){
        return postJpaRepo.findAll();
    }
}
