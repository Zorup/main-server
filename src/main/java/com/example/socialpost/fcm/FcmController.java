package com.example.socialpost.fcm;

import com.example.socialpost.common.response.CommonResult;
import com.example.socialpost.common.response.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Api(tags={"99. FCM 메시지 핸들러"})
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/v1")
public class FcmController {
    private final FCMService fcmService;
    private final ResponseService responseService;

    @PostMapping("/fcm-test")
    @ApiOperation(value="fcm message test 전송", notes = "멘션 또는 채팅 빌송 시 fcm 메시지 전송")
    public CommonResult postPushMessage(String pushToken){
        Map<String, String> eventMap = new HashMap<>();
        eventMap.put("test", "test");
        fcmService.sendDataByToken(pushToken, eventMap);
        return responseService.getSuccessResult();
    }
}
