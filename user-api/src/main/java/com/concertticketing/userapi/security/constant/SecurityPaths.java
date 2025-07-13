package com.concertticketing.userapi.security.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityPaths {
    public static final String AUTH_PATH = "/api/v1/auth/**";
}
