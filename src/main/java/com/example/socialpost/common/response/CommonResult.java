package com.example.socialpost.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//차후 예외처리 및 결과 데이터 구조의 정형화를 위한 클래스 입니다.
@Getter
@Setter
public class CommonResult {
    @ApiModelProperty(value = "응답 성공 여부 :  true / false")
    private boolean success;

    @ApiModelProperty(value = "응답 코드 번호 >=0 정상, <0 비정상")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;
}
