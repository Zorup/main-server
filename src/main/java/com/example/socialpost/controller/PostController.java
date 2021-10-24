package com.example.socialpost.controller;

import com.example.socialpost.common.response.ListResult;
import com.example.socialpost.common.response.ResponseService;
import com.example.socialpost.common.response.SingleResult;
import com.example.socialpost.domain.Image;
import com.example.socialpost.domain.Post;
import com.example.socialpost.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Api(tags={"1. 게시글 관리 Api"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/v1")
public class PostController {
    private final PostService postService;
    private final ResponseService responseService;

    @ApiOperation(value = "게시글 생성", notes = "해당 소그룹피드에 게시글을 추가합니다.")
    @PostMapping(value="/post")
    public SingleResult<Post.PostResponse> savePost(@CookieValue(value = "X-Auth-Token") Cookie cookie,
                                                    @ModelAttribute Post.PostRequest post,
                                                    @ModelAttribute Image.ImageRequestForm imageForm) throws IOException {
        return responseService.getSingleResult(postService.createPost(cookie, post, imageForm));
    }

    @ApiOperation(value = "게시글 조회", notes = "테스트용 ")
    @GetMapping(value="/post")
    public ListResult<Post> getAllPost(){
        return responseService.getListResult(postService.getAllPosts());
    }

    @ApiOperation(value = "게시판내 게시글 조회", notes = "게시글 조회")
    @GetMapping(value="/forum/{forumId}/posts")
    public ListResult<Post> getPosts(@PathVariable("forumId") Long forumId){
        return responseService.getListResult(postService.getForumPosts(forumId));
    }

    @ApiOperation(value = "게시판내 게시글 조회", notes = "게시글과 게시글에 있는 덧글 조회")
    @GetMapping(value="/forum/{forumId}/postview")
    public ListResult<Post.PostResponse> getPostview(@PathVariable("forumId") Long forumId){
        List<Post.PostResponse> postList = postService.getPostLists(forumId);
        Collections.reverse(postList);  // 결과물이 날짜별로 오름차순 정렬되어있으므로 내림차순으로 뒤집기
        return responseService.getListResult(postList);
    }

    @ApiOperation(value = "특정 게시글 하나 조회", notes = "특정 게시글 하나와 게시글에 있는 댓글들 조회")
    @GetMapping(value="/post/{postId}")
    public SingleResult<Post.PostResponse> getPost(@PathVariable("postId") Long postId){
        return responseService.getSingleResult(postService.getPost(postId));
    }
}
