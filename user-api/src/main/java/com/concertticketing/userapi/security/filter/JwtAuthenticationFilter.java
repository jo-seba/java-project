package com.concertticketing.userapi.security.filter;

import static com.concertticketing.userapi.security.constant.SecurityConstants.*;
import static com.concertticketing.userapi.security.provider.JwtProvider.TokenType.*;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.concertticketing.userapi.security.authentication.JwtAuthenticationToken;
import com.concertticketing.userapi.security.provider.JwtProvider;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String token = getToken(request);

        Optional.ofNullable(token)
            .flatMap(t -> jwtProvider.parseClaimsAndValidate(t, ACCESS))
            .map(Claims::getSubject)
            .map(Long::parseLong)
            .map(JwtAuthenticationToken::new)
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
