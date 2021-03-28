package com.example.socialpost.controller;

import com.example.socialpost.common.security.JwtTokenProvider;
import com.example.socialpost.domain.Post;
import com.example.socialpost.domain.User;
import com.example.socialpost.service.PostService;
import com.example.socialpost.service.UserService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    UserService userService;

    @GetMapping("/template")
    public ModelAndView viewPost(@CookieValue(value = "X-Auth-Token") Cookie cookie){
        User user = userService.getInfoBytoken(cookie.getValue());
        ModelAndView mv = new ModelAndView();
        List<Post> postList = postService.getAllPosts();
        mv.setViewName("temp");
        mv.addObject("user", user);
        mv.addObject("postList", postList);
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
}