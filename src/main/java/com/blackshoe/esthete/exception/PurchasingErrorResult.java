package com.blackshoe.esthete.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PurchasingErrorResult {

    ALREADY_PURCHASED(HttpStatus.BAD_REQUEST, "이미 구매한 상품입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
