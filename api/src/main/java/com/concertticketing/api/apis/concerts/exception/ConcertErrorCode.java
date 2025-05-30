package com.concertticketing.api.apis.concerts.exception;

import com.concertticketing.api.common.exception.BaseErrorCode;
import com.concertticketing.api.common.exception.constant.StatusCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConcertErrorCode implements BaseErrorCode {
    // 404 NOT_FOUND
    CONCERT_NOT_FOUND(StatusCode.NOT_FOUND, "concert not found"),
    PAGE_OVERFLOW(StatusCode.NOT_FOUND, "invalid page"),
    ;

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
