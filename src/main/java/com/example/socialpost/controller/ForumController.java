package com.example.socialpost.controller;

import com.example.socialpost.common.response.CommonResult;
import com.example.socialpost.common.response.ResponseService;
import com.example.socialpost.service.ForumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags= {"3. 게시판 관리 Api"})
@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping(value = "/v1")
public class ForumController {
    @Autowired
    ForumService forumService;
    @Autowired
    ResponseService responseService;

    @ApiOperation(value = "게시판 생성", notes = "게시판을 추가합니다.")
    @PostMapping("/forum")
    public CommonResult addForum(@ApiParam(value= "게시판 제목",required = true) @RequestParam String forumName){
        forumService.addForum(forumName);
        return responseService.getSuccessResult();
    }
}
