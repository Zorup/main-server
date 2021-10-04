package com.example.socialpost.controller;

import com.example.socialpost.common.response.CommonResult;
import com.example.socialpost.common.response.ResponseService;
import com.example.socialpost.common.response.SingleResult;
import com.example.socialpost.domain.Forum;
import com.example.socialpost.service.ForumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public SingleResult<Forum> addForum(@ApiParam(value= "게시판 제목",required = true) @RequestParam String forumName){
        return responseService.getSingleResult(forumService.addForum(forumName));
    }

    @ApiOperation(value = "게시판 정보 조회", notes = "게시판 목록 및 디폴트 게시판을 조회합니다.")
    @GetMapping("/forum")
    public Map<String, Object> getForumInfo(){
        Forum defaultForum = forumService.getDefaultForum(1L);  // 일단 무조건 1번 리턴중. 후에 디폴트포럼 리턴하게 변경필요
        List<Forum> forumList = forumService.findAllForum();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("defaultForum", defaultForum);
        result.put("forumList", forumList);

        return result;
    }

    @ApiOperation(value = "게시판 삭제", notes = "선택된 게시판들을 삭제합니다.")
    @DeleteMapping("/forum")
    public CommonResult deleteForum(@RequestParam(value="forumId") List<Long> forumId){
        forumService.deleteForum(forumId);
        return responseService.getSuccessResult();
    }

}
