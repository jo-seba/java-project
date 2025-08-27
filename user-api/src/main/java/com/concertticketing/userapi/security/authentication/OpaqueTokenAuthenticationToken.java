package com.concertticketing.userapi.security.authentication;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;

public class OpaqueTokenAuthenticationToken extends AbstractAuthenticationToken {
    private final ConcertTokenUserCache concertTokenUserCache;

    public OpaqueTokenAuthenticationToken(ConcertTokenUserCache concertTokenUserCache) {
        super(Collections.emptyList());
        this.concertTokenUserCache = concertTokenUserCache;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return concertTokenUserCache;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
