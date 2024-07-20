package com.blackshoe.esthete.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecommendErrorResult {

    NOT_FOUND_TAG_ID(HttpStatus.NOT_FOUND, "존재하지 않거나 추가하지 않은 선호 태그입니다."),
    ALREADY_EXIST_TAG_ID(HttpStatus.BAD_REQUEST, "이미 추가한 선호 태그입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
