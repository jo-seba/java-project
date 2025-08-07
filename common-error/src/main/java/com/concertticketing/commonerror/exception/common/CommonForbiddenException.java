package com.concertticketing.commonerror.exception.common;

import com.concertticketing.commonerror.exception.GlobalErrorException;

public class CommonForbiddenException extends GlobalErrorException {
    public CommonForbiddenException() {
        super(403, "Forbidden");
    }
}
