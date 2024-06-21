package com.blackshoe.esthete.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FilterException extends RuntimeException{
    private final FilterErrorResult filterErrorResult;

    @Override
    public String getMessage() {
        return filterErrorResult.getMessage();
    }
}
