package com.concertticketing.api.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    OK(200, "success"),
    CREATED(201, "success"),
    NO_CONTENT(204, "success"),

    BAD_REQUEST(400, "failure"),
    UNAUTHORIZED(401, "failure"),
    FORBIDDEN(403, "failure"),
    NOT_FOUND(404, "failure"),
    METHOD_NOT_ALLOWED(405, "failure"),
    CONFLICT(409, "failure"),

    INTERNAL_SERVER_ERROR(500, "internal server error");

    private final int code;
    private final String message;
}
