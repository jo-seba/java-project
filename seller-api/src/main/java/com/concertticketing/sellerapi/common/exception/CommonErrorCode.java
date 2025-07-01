package com.concertticketing.sellerapi.common.exception;

import com.concertticketing.sellerapi.common.exception.constant.StatusCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonErrorCode implements BaseErrorCode {
    BAD_REQUEST(StatusCode.BAD_REQUEST, "bad request"),
    UNAUTHORIZED(StatusCode.UNAUTHORIZED, "unauthorized"),
    FORBIDDEN(StatusCode.FORBIDDEN, "forbidden"),
    NOT_FOUND(StatusCode.NOT_FOUND, "not found"),
    CONFLICT(StatusCode.CONFLICT, "conflict");

    private final StatusCode statusCode;
    private final String message;

    @Override
    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
