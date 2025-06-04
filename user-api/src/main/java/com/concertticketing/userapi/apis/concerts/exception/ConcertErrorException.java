package com.concertticketing.userapi.apis.concerts.exception;

import com.concertticketing.userapi.common.exception.GlobalErrorException;

public class ConcertErrorException extends GlobalErrorException {
    public ConcertErrorException(ConcertErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
