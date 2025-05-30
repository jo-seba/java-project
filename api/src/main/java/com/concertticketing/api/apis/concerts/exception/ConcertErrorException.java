package com.concertticketing.api.apis.concerts.exception;

import com.concertticketing.api.common.exception.BaseErrorCode;
import com.concertticketing.api.common.exception.GlobalErrorException;

public class ConcertErrorException extends GlobalErrorException {
    public ConcertErrorException(ConcertErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
