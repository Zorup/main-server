package com.example.socialpost.controller;

import com.example.socialpost.common.response.CommonResult;
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
        log.info("content: " + post.getContent());
        log.info("forumId: " + post.getForumId());
        log.info("groupId: " + post.getGroupId());
        return responseService.getSingleResult(postService.createPost(cookie, post, imageForm));
    }

    @ApiOperation(value = "게시글 조회", notes = "테스트용 ")
    @GetMapping(value="/post")
    public ListResult<Post> getAllPost(){
        return responseService.getListResult(postService.getAllPosts());
    }

    @ApiOperation(value = "게시판내 게시글 조회", notes = "게시글 조회")
    @GetMapping(value="/forum/{forumId}/posts")
    public ListResult<Post> getPosts(@PathVariable("forumId") Long forumId,
                                     @RequestParam(required = false) Long oldestId,
                                     @RequestParam(required = false) Integer pageSize){
        return responseService.getListResult(postService.getForumPosts(forumId, oldestId, pageSize));
    }

    @ApiOperation(value = "게시판내 게시글 조회", notes = "게시글과 게시글에 있는 덧글 조회")
    @GetMapping(value="/forum/{forumId}/postview")
    public ListResult<Post.PostResponse> getPostview(@PathVariable("forumId") Long forumId,
                                                     @RequestParam(required = false) Long oldestId,
                                                     @RequestParam(required = false) Integer pageSize){
        List<Post.PostResponse> postList = postService.getPostLists(forumId, oldestId, pageSize);

        return responseService.getListResult(postList);
    }

    @ApiOperation(value = "특정 게시글 하나 조회", notes = "특정 게시글 하나와 게시글에 있는 댓글들 조회")
    @GetMapping(value="/post/{postId}")
    public SingleResult<Post.PostResponse> getPost(@PathVariable("postId") Long postId){
        return responseService.getSingleResult(postService.getPost(postId));
    }

    @ApiOperation(value = "게시글 삭제", notes = "특정 게시글 하나를 삭제")
    @DeleteMapping(value="/post/{postId}")
    public CommonResult deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "게시글 수정", notes = "특정 게시글 하나를 수정")
    @PatchMapping("/post/{postId}")
    public SingleResult<Post.PostResponse> updatePost(@ModelAttribute Post.PostRequest post,
                                                     @ModelAttribute Image.ImageRequestForm imageForm,
                                                      @PathVariable("postId") Long postId) {
        return responseService.getSingleResult(postService.updatePost(post, imageForm, postId));
    }
}
