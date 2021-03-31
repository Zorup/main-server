package com.example.socialpost.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//차후 예외처리 및 결과 데이터 구조의 정형화를 위한 클래스 입니다.
@Getter
@Setter
public class ListResult<T> extends CommonResult {
    private List<T> list;
}
