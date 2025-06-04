package com.concertticketing.userapi.common.exception;

import com.concertticketing.userapi.common.exception.constant.StatusCode;

public interface BaseErrorCode {
    StatusCode getStatusCode();

    String getMessage();
}
