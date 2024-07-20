package com.blackshoe.esthete.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecommendException extends RuntimeException{
    private final RecommendErrorResult recommendErrorResult;

    @Override
    public String getMessage() {
        return recommendErrorResult.getMessage();
    }
}
