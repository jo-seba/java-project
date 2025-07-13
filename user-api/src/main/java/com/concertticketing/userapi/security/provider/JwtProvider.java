package com.concertticketing.userapi.security.provider;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.concertticketing.userapi.common.property.JwtProperty;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
    private final Map<TokenType, SecretKey> secretKeyMap = new EnumMap<>(TokenType.class);
    private final Map<TokenType, Long> expirationMap = new EnumMap<>(TokenType.class);

    public enum TokenType {
        ACCESS, REFRESH
    }

    public JwtProvider(JwtProperty jwtProperty) {
        this.secretKeyMap.put(
            TokenType.ACCESS,
            Keys.hmacShaKeyFor(
                jwtProperty.accessTokenSecretKey().getBytes(StandardCharsets.UTF_8)
            )
        );
        this.secretKeyMap.put(
            TokenType.REFRESH,
            Keys.hmacShaKeyFor(
                jwtProperty.refreshTokenSecretKey().getBytes(StandardCharsets.UTF_8)
            )
        );

        this.expirationMap.put(
            TokenType.ACCESS,
            jwtProperty.accessTokenExpire()
        );
        this.expirationMap.put(
            TokenType.REFRESH,
            jwtProperty.refreshTokenExpire()
        );
    }

    public String generateToken(Long userId, TokenType tokenType) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(new Date(now))
            .expiration(new Date(now + expirationMap.get(tokenType)))
            .signWith(secretKeyMap.get(tokenType))
            .compact();
    }

    public Optional<Claims> parseClaimsAndValidate(String token, TokenType tokenType) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(secretKeyMap.get(tokenType))
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
