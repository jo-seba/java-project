package com.concertticketing.sellerapi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.concertticketing.sellerapi.common.exception.constant.StatusCode;
import com.concertticketing.sellerapi.common.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalErrorException(GlobalErrorException e) {
        if (e.getBaseErrorCode().getStatusCode().getCode() >= StatusCode.INTERNAL_SERVER_ERROR.getCode()) {
            // sentry - monitoring
        }
        return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.of(e.getStatusMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        {
            HandlerMethodValidationException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
        }
    )
    protected ErrorResponse handleBadRequestError(RuntimeException e) {
        return ErrorResponse.of(StatusCode.BAD_REQUEST.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        {
            MethodArgumentNotValidException.class
        }
    )
    protected ErrorResponse handleBadRequestError(Exception e) {
        return ErrorResponse.of(StatusCode.BAD_REQUEST.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(
        {
            AuthorizationDeniedException.class
        }
    )
    protected ErrorResponse handleForbiddenError(RuntimeException e) {
        return ErrorResponse.of(StatusCode.FORBIDDEN.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        {
            NoResourceFoundException.class
        }
    )
    protected ErrorResponse handleNotFoundError(Exception e) {
        return ErrorResponse.of(StatusCode.NOT_FOUND.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(
        {
            HttpRequestMethodNotSupportedException.class
        }
    )
    protected ErrorResponse handleMethodNotAllowedError(Exception e) {
        return ErrorResponse.of(StatusCode.METHOD_NOT_ALLOWED.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleException(Exception e) {
        e.printStackTrace();
        return ErrorResponse.of(StatusCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}

