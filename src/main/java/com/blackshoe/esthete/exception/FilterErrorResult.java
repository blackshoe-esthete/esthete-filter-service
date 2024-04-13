package com.blackshoe.esthete.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FilterErrorResult {
    NOT_FOUND_FILTER(HttpStatus.NOT_FOUND, "존재하지 않는 필터입니다."),
    INVALID_SORT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 정렬 요청입니다." ),
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND, "존재하지 않는 태그입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
