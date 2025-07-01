package com.concertticketing.sellerapi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GlobalErrorException extends RuntimeException {
    private final BaseErrorCode baseErrorCode;

    public int getStatusCode() {
        return baseErrorCode.getStatusCode().getCode();
    }

    public String getStatusMessage() {
        return baseErrorCode.getStatusCode().getMessage();
    }

    @Override
    public String toString() {
        return "GlobalErrorException(statusCode=" + baseErrorCode.getStatusCode().getCode()
            + ", message=" + baseErrorCode.getMessage() + ")";
    }
}

