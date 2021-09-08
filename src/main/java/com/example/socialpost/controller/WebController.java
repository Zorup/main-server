package com.example.socialpost.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class WebController {
    @GetMapping("/test") //차후 forum Id를 pathvariable로 부여할 것.
    public ModelAndView test(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}