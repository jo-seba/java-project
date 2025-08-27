package com.concertticketing.sellerapi.security.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityPaths {
    public static final String AUTH_PATH = "/api/v1/auth/**";
    public static final String[] SWAGGER_PATHS = {"/v3/api-docs/**", "/swagger-ui/**"};
}
