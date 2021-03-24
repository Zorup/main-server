package com.example.socialpost.controller;

import com.example.socialpost.domain.User;
import com.example.socialpost.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags={"2. 유저 Api"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/v1")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "유저 아이디 생성", notes = "사용자가 정보를 입력해 회원 가입을 합니다.")
    @PostMapping(value="/singin")
    public User signIn(@ModelAttribute User.SignRequest rq){
        return userService.signIn(rq);
    }

    @ApiOperation(value = "로그인", notes = "사용자가 아이디와 패스워드를 입력하여 로그인 합니다.")
    @PostMapping(value="/login")
    public User.UserResponse login(@ModelAttribute User.LoginRequest rq){
        return userService.login(rq);
    }
}
