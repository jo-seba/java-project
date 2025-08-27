package com.concertticketing.userapi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.concertticketing.commonerror.exception.GlobalErrorException;
import com.concertticketing.userapi.common.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalErrorException(GlobalErrorException e) {
        if (e.getStatusCode() >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            // sentry - monitoring
        }
        return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.of(e.getMessage()));
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
        return ErrorResponse.of("Bad Request");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        {
            MethodArgumentNotValidException.class
        }
    )
    protected ErrorResponse handleBadRequestError(Exception e) {
        return ErrorResponse.of("Bad Request");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        {
            NoResourceFoundException.class
        }
    )
    protected ErrorResponse handleNotFoundError(Exception e) {
        return ErrorResponse.of("Not Found");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(
        {
            HttpRequestMethodNotSupportedException.class
        }
    )
    protected ErrorResponse handleMethodNotAllowedError(Exception e) {
        return ErrorResponse.of("Method Not Allowed");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleException(Exception e) {
        e.printStackTrace();
        return ErrorResponse.of("Error");
    }
}
