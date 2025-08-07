package com.concertticketing.userapi.security.authentication;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Long userId;

    public JwtAuthenticationToken(Long userId) {
        super(Collections.emptyList());
        this.userId = userId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
