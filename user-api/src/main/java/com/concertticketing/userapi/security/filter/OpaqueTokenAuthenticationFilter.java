package com.concertticketing.userapi.security.filter;

import static com.concertticketing.userapi.security.constant.SecurityConstants.*;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.concertticketing.userapi.apis.concerts.service.ConcertCacheSearchService;
import com.concertticketing.userapi.security.authentication.OpaqueTokenAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OpaqueTokenAuthenticationFilter extends OncePerRequestFilter {
    private final ConcertCacheSearchService concertCacheSearchService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getToken(request);

        Optional.ofNullable(token)
            .flatMap(concertCacheSearchService::getConcertTokenUser)
            .map(OpaqueTokenAuthenticationToken::new)
            .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_START)) {
            return authorizationHeader.substring(BEARER_START_LENGTH);
        }
        return null;
    }
}
