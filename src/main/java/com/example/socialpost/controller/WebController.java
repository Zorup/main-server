package com.example.socialpost.controller;

import com.example.socialpost.domain.Forum;
import com.example.socialpost.domain.Post;
import com.example.socialpost.domain.User;
import com.example.socialpost.repository.ForumJpaRepo;
import com.example.socialpost.service.CommentService;
import com.example.socialpost.service.ForumService;
import com.example.socialpost.service.PostService;
import com.example.socialpost.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
public class WebController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    ForumService forumService;
    @Autowired
    CommentService commentService;
    @Autowired
    ForumJpaRepo forumJpaRepo;

    @GetMapping("/template") //차후 forum Id를 pathvariable로 부여할 것.
    public ModelAndView viewPost(@CookieValue(value = "X-Auth-Token") Cookie cookie){
        User user = userService.getInfoBytoken(cookie.getValue());
        Forum currentForum = forumJpaRepo.findById(1L).get();  //차후 항상 default forum = 1로 설정?
        List<Forum> forumList = forumService.findAllForum(); //차후 그룹명에 해당하는 forum만 불러오도록
        List<Post.PostResponse> postList = postService.getPostLists(1L);
        Collections.reverse(postList);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("temp");
        mv.addObject("user", user);
        mv.addObject("postList", postList); //차후 default 게시글 or 게시판 존재시 첫 게시판에 있는 feed목록 load
        mv.addObject("forumList", forumList);
        mv.addObject("currentForum", currentForum);
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView joinPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("register");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/forgot")
    public ModelAndView forgotPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forgot");
        return mv;
    }

    @PostMapping("/v1/web/comment")
    @ResponseBody
    public ModelAndView addComment(@CookieValue(value = "X-Auth-Token") Cookie cookie,
                                   @RequestParam(value = "postId") Long postId,
                                   @RequestParam(value = "content") String content,
                                   @RequestParam(value = "forumId") Long forumId){
        commentService.addComment(cookie, postId, content);
        String redirectUrl = "redirect:/template/"+forumId;
        log.info("log :: event success target post Id : " + postId + " content : " + content);

        ModelAndView mv = new ModelAndView();
        mv.setViewName(redirectUrl);
        return mv;
    }

    @PostMapping("/v1/web/post")
    public ModelAndView addPost(@CookieValue(value = "X-Auth-Token") Cookie cookie,
                                @ModelAttribute Post.PostRequest post){
        String redirectUrl = "redirect:/template/"+post.getForumId();

        postService.createPost(cookie, post);
        ModelAndView mv = new ModelAndView();
        mv.setViewName(redirectUrl);
        return mv;
    }

    @GetMapping("/template/{forumId}") //차후 forum Id를 pathvariable로 부여할 것.
    public ModelAndView viewPostByforumId(@CookieValue(value = "X-Auth-Token") Cookie cookie, @PathVariable(name="forumId")Long forumId){
        log.info("log :: select forum api start ");
        Forum currentForum = forumJpaRepo.findById(forumId).get();
        User user = userService.getInfoBytoken(cookie.getValue());
        List<Forum> forumList = forumService.findAllForum();
        List<Post.PostResponse> postList = postService.getPostLists(forumId);
        Collections.reverse(postList);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("temp");
        mv.addObject("user", user);
        mv.addObject("postList", postList); //차후 default 게시글 or 게시판 존재시 첫 게시판에 있는 feed목록 load
        mv.addObject("forumList", forumList);
        mv.addObject("currentForum", currentForum);
        return mv;
    }
}