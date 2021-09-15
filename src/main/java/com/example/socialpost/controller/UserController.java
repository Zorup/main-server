package com.example.socialpost.controller;

import com.example.socialpost.common.response.CommonResult;
import com.example.socialpost.common.response.ListResult;
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

    @ApiOperation(value = "유저 정보 반환", notes = "토큰을 기반으로 사용자의 정보를 반환합니다.")
    @GetMapping(value="/user-info")
    public SingleResult<User> getUserInfo(@CookieValue(value = "X-Auth-Token") Cookie cookie){
        return responseService.getSingleResult(userService.getInfoBytoken(cookie.getValue()));
    }

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
        //FCM관련 푸쉬토큰 기능 추가시 해당 영역 코드 추가 필요 가능성 존재
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
        //FCM관련 푸쉬토큰 기능 추가시 해당 영역 코드 추가 필요 가능성 존재
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "그룹내 유저 리스트 반환", notes = "그룹내 존재하는 사용자 리스트를 반환합니다.")
    @GetMapping(value="/group/users")
    public ListResult<User.UserMentionResponse> getGroupUsers(@CookieValue(value = "X-Auth-Token") Cookie cookie){
        return responseService.getListResult(userService.getAllUserInForum());
    }

    @ApiOperation(value = "FCM 토큰 사용자 ID 변환", notes = "FCM 토큰이 어떤 사용자의 것인지 체크합니다.")
    @GetMapping(value="/user")
    public SingleResult<Long> getUserIdByFcmToken(@RequestParam(name = "push-token") String pushToken){
        return responseService.getSingleResult(userService.getUserIdByFcmToken(pushToken));
    }

    @ApiOperation(value = "FCM 토큰 저장", notes = "로그인시 사용자의 FCM 토큰을 재설정합니다. ")
    @PatchMapping(value="/user/{userId}")
    public CommonResult setUserPushToken(@CookieValue(value = "X-Auth-Token") Cookie cookie, @PathVariable Long userId,
                                         @RequestParam(name = "push-token")String pushToken){
        log.info("토큰 저장 로직 호출 완료");
        userService.setUserPushToken(userId, pushToken);
        return responseService.getSuccessResult();
    }
}
