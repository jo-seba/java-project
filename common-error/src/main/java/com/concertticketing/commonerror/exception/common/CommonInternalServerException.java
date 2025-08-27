package com.concertticketing.commonerror.exception.common;

import com.concertticketing.commonerror.exception.GlobalErrorException;

public class CommonInternalServerException extends GlobalErrorException {
    public CommonInternalServerException(Exception e) {
        // TODO: sentry capture
        super(500, "Internal Server Error");
    }

    public CommonInternalServerException(String message) {
        // TODO: sentry capture message
        super(500, "Internal Server Error");
    }
}
