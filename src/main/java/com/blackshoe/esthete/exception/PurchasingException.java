package com.blackshoe.esthete.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PurchasingException extends RuntimeException{
    private final PurchasingErrorResult purchasingErrorResult;

    @Override
    public String getMessage() {
        return purchasingErrorResult.getMessage();
    }
}
