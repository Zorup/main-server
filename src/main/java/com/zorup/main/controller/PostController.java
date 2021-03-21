package com.zorup.main.controller;

import com.zorup.main.domain.Post;
import com.zorup.main.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"1. 게시글 관리 Api"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/v1")
public class PostController {
    private final PostService postService;

//    @ApiOperation(value = "게시글 생성", notes = "해당 소그룹피드에 게시글을 추가합니다.")
//    @PostMapping(value="/post")
//    public Post savePost(@ModelAttribute Post post){ return postService.createPost(post); }

    @ApiOperation(value = "게시글 조회", notes = "테스트용 ")
    @GetMapping(value="/post")
    public List<Post> getAllPost(){
        return postService.getAllPosts();
    }
}
