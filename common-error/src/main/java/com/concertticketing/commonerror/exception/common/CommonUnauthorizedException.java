package com.concertticketing.commonerror.exception.common;

import com.concertticketing.commonerror.exception.GlobalErrorException;

public class CommonUnauthorizedException extends GlobalErrorException {
    public CommonUnauthorizedException() {
        super(401, "Unauthorized");
    }
}
