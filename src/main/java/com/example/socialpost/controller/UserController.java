package com.example.socialpost.controller;

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

import javax.servlet.http.Cookie;

@Slf4j
@Api(tags={"2. 유저 Api"})
@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping(value ="/v1")
public class UserController {
    private final UserService userService;
    private final ResponseService responseService;

    // TODO 프론트 react 전환시 삭제될 API
    @ApiOperation(value = "유저 정보 반환", notes = "토큰을 기반으로 사용자의 정보를 반환합니다.")
    @GetMapping(value="/user-info")
    public SingleResult<User> getUserInfo(@CookieValue(value = "X-Auth-Token") Cookie cookie){
        return responseService.getSingleResult(userService.getInfoBytoken(cookie.getValue()));
    }

    @ApiOperation(value = "그룹내 유저 리스트 반환", notes = "그룹내 존재하는 사용자 리스트를 반환합니다.")
    @GetMapping(value="/group/users")
    public ListResult<User.UserMentionResponse> getGroupUsers(){
        return responseService.getListResult(userService.getAllUserInForum());
    }

}
