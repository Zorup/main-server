package com.example.socialpost.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//차후 예외처리 및 결과 데이터 구조의 정형화를 위한 클래스 입니다.
public class SingleResult<T> extends CommonResult{
    private T data;
}
