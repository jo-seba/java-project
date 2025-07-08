package com.concertticketing.commonerror.exception.common;

import com.concertticketing.commonerror.exception.GlobalErrorException;

public class CommonNotFoundException extends GlobalErrorException {
    public CommonNotFoundException() {
        super(404, "Not Found");
    }
}
