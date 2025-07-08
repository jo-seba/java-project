package com.concertticketing.sellerapi.apis.auth.usecase;

import static com.concertticketing.sellerapi.security.provider.JwtProvider.TokenType.*;

import com.concertticketing.commonerror.exception.common.CommonUnauthorizedException;
import com.concertticketing.sellerapi.apis.auth.dto.TokenDto.TokenRes;
import com.concertticketing.sellerapi.apis.sellers.service.SellerSearchService;
import com.concertticketing.sellerapi.common.annotation.UseCase;
import com.concertticketing.sellerapi.security.provider.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class AuthUseCase {
    private final JwtProvider jwtProvider;

    private final SellerSearchService sellerSearchService;

    public TokenRes generateAccessToken(String refreshToken) {
        if (!jwtProvider.isTokenValid(refreshToken, REFRESH)) {
            throw new CommonUnauthorizedException();
        }

        Integer sellerId = jwtProvider.extractSellerId(refreshToken, REFRESH);

        sellerSearchService.find(sellerId);

        return new TokenRes(
            jwtProvider.generateToken(sellerId, ACCESS)
        );
    }
}
