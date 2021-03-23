package com.example.socialpost.controller;

import com.example.socialpost.domain.Post;
import com.example.socialpost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    PostService postService;

    @GetMapping("/template")
    public ModelAndView viewPost(){
        ModelAndView mv = new ModelAndView();
        List<Post> postList = postService.getAllPosts();
        mv.setViewName("temp");
        mv.addObject("postList", postList);
        return mv;
    }
}