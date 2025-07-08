package com.concertticketing.commonerror.exception.common;

import com.concertticketing.commonerror.exception.GlobalErrorException;

public class CommonBadRequestException extends GlobalErrorException {
    public CommonBadRequestException() {
        super(400, "Bad Request");
    }
}
