package com.concertticketing.commonerror.exception.common;

import com.concertticketing.commonerror.exception.GlobalErrorException;

public class CommonConflictException extends GlobalErrorException {
    public CommonConflictException() {
        super(409, "Conflict");
    }
}
