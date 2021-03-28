package com.example.socialpost.controller;

import com.example.socialpost.domain.Post;
import com.example.socialpost.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.net.http.HttpRequest;
import java.util.List;

@Slf4j
@Controller
public class WebController {
    @Autowired
    PostService postService;

    @GetMapping("/template")
    public ModelAndView viewPost(@CookieValue(value = "X-Auth-Token") Cookie cookie){
        log.info(cookie.getValue());
        
        ModelAndView mv = new ModelAndView();
        List<Post> postList = postService.getAllPosts();
        mv.setViewName("temp");
        mv.addObject("postList", postList);
        return mv;
    }
}