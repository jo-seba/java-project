package com.concertticketing.api.common.exception;

import com.concertticketing.api.common.exception.constant.StatusCode;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    StatusCode getStatusCode();
    String getMessage();
}
