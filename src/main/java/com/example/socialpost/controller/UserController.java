package com.example.socialpost.controller;

import com.example.socialpost.common.response.CommonResult;
import com.example.socialpost.common.response.ResponseService;
import com.example.socialpost.common.response.SingleResult;
import com.example.socialpost.domain.User;
import com.example.socialpost.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Api(tags={"2. 유저 Api"})
@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping(value ="/v1")
public class UserController {
    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "유저 아이디 생성", notes = "사용자가 정보를 입력해 회원 가입을 합니다.")
    @PostMapping(value="/signin")
    public SingleResult<User> signIn(@ModelAttribute User.SignRequest rq){
        log.info(rq.getLoginId());
        return responseService.getSingleResult(userService.signIn(rq));
    }

    @ApiOperation(value = "로그인", notes = "사용자가 아이디와 패스워드를 입력하여 로그인 합니다.")
    @PostMapping(value="/login")
    public CommonResult login(@ModelAttribute User.LoginRequest rq, HttpServletResponse response){
        log.info("call login Api");
        String token = userService.login(rq);
        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName("X-Auth-Token");
        cg.setCookieMaxAge(-1);
        cg.setCookieHttpOnly(true);
        cg.addCookie(response,token);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "로그아웃", notes = "사용자가 로그아웃 합니다")
    @PostMapping(value="/logout")
    public CommonResult logout(HttpServletResponse response){
        log.info("call logout Api");
        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName("X-Auth-Token");
        cg.setCookieMaxAge(0);
        cg.setCookieHttpOnly(true);
        cg.addCookie(response,null);
        return responseService.getSuccessResult();
    }
}
