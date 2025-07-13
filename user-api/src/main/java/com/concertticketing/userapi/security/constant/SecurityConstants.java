package com.concertticketing.userapi.security.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConstants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_START = "Bearer ";
    public static final int BEARER_START_LENGTH = BEARER_START.length();
}
