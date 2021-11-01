package com.example.socialpost.controller;

import com.example.socialpost.common.response.CommonResult;
import com.example.socialpost.common.response.ResponseService;
import com.example.socialpost.common.response.SingleResult;
import com.example.socialpost.domain.Comment;
import com.example.socialpost.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@Slf4j
@RestController
@Api(tags={"4.덧글관리"})
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/v1")
public class CommentController {
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final ResponseService responseService;

    @ApiOperation(value = "덧글 생성", notes = "게시글에 덧글을 추가합니다.")
    @PostMapping("/comment")
    public SingleResult<Comment.CommentResponse> addComment(@CookieValue(value = "X-Auth-Token") Cookie cookie,
                                            @RequestParam(value = "postId") Long postId,
                                            @RequestParam(value = "content") String content){
        return responseService.getSingleResult(commentService.addComment(cookie, postId, content));
    }

    @ApiOperation(value = "덧글 삭제", notes = "특정 덧글 하나를 삭제합니다.")
    @DeleteMapping("/comment/{commentId}")
    public CommonResult deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return responseService.getSuccessResult();
    }
}
