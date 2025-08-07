package com.concertticketing.commonerror.exception;

import lombok.Getter;

@Getter
public class GlobalErrorException extends RuntimeException {
    private final int statusCode;

    public GlobalErrorException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "GlobalErrorException(statusCode=" + getStatusCode() + ", message=" + getMessage() + ")";
    }
}
