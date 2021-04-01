package com.example.socialpost.service;

import com.example.socialpost.common.security.JwtTokenProvider;
import com.example.socialpost.domain.Comment;
import com.example.socialpost.domain.Post;
import com.example.socialpost.domain.User;
import com.example.socialpost.repository.CommentJpaRepo;
import com.example.socialpost.repository.PostJpaRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    PostJpaRepo postJpaRepo;
    @Autowired
    UserService userService;
    @Autowired
    CommentJpaRepo commentJpaRepo;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public Comment addComment(@CookieValue(value = "X-Auth-Token") Cookie cookie, Long postId, String content){
        log.info("Log :: addComment start..");
        Post p = postJpaRepo.findById(postId).get();
        User u = userService.getInfoBytoken(cookie.getValue());

        Comment newItem = Comment.builder()
                .content(content)
                .user(u).post(p)
                .build();
       // p.getComments().add(newItem);
        return commentJpaRepo.save(newItem);
    }
}
