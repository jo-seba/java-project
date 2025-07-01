package com.concertticketing.sellerapi.common.exception;

import com.concertticketing.sellerapi.common.exception.constant.StatusCode;

public interface BaseErrorCode {
    StatusCode getStatusCode();

    String getMessage();
}

