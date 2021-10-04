package com.example.socialpost.controller;

import com.example.socialpost.common.response.ResponseService;
import com.example.socialpost.common.response.SingleResult;
import com.example.socialpost.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@Slf4j
@Api(tags = {"5. 좋아요 관리"})
@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class LikeController {
    private final ResponseService responseService;
    private final LikeService likeService;

    @ApiOperation(value="좋아요 생성", notes = "좋아요를 생성합니다.")
    @PostMapping("/like")
    public SingleResult<Integer> createLike(@CookieValue(value = "X-Auth-Token") Cookie cookie, @RequestParam Long postId){
        log.info("like controller start..");
        return responseService.getSingleResult(likeService.clickLikeEvent(cookie,postId));
    }
}
