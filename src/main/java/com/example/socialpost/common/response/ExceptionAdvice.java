package com.example.socialpost.common.response;


import javax.servlet.http.HttpServletRequest;

import com.example.socialpost.common.exception.*;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.info("defaultException:"+e.toString());
        if (e.toString().contains("org.springframework.security.access.AccessDeniedException")) {
            return responseService.getFailResult(Integer.valueOf(getMessage("validationTokenError.code")), getMessage("validationTokenError.msg"));
        } else {
            return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
        }
    }

    @ExceptionHandler(HUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, HUserNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult validationException(HttpServletRequest request, Exception e) {
        log.info("validationException:"+e.toString());
        return responseService.getFailResult(Integer.valueOf(getMessage("validationError.code")), getMessage("validationError.msg"));
    }

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}