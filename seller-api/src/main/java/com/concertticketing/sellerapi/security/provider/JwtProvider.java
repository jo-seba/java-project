package com.concertticketing.sellerapi.security.provider;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.concertticketing.sellerapi.common.property.JwtProperty;

import io.jsonwebtoken.JwtException;
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

    public String generateToken(Integer sellerId, TokenType tokenType) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
            .subject(sellerId.toString())
            .issuedAt(new Date(now))
            .expiration(new Date(now + expirationMap.get(tokenType)))
            .signWith(secretKeyMap.get(tokenType))
            .compact();
    }

    public boolean isTokenValid(String token, TokenType tokenType) {
        try {
            Jwts.parser()
                .verifyWith(secretKeyMap.get(tokenType))
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Integer extractSellerId(String token, TokenType tokenType) {
        return Integer.parseInt(Jwts.parser()
            .verifyWith(secretKeyMap.get(tokenType))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject()
        );
    }
}
