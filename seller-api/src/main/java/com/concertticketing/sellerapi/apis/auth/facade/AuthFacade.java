package com.concertticketing.sellerapi.apis.auth.facade;

import static com.concertticketing.sellerapi.security.provider.JwtProvider.TokenType.*;

import com.concertticketing.commonerror.exception.common.CommonUnauthorizedException;
import com.concertticketing.sellerapi.apis.auth.dto.TokenDto;
import com.concertticketing.sellerapi.apis.sellers.service.SellerService;
import com.concertticketing.sellerapi.common.annotation.Facade;
import com.concertticketing.sellerapi.security.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class AuthFacade {
    private final JwtProvider jwtProvider;

    private final SellerService sellerService;

    public TokenDto.TokenRes generateAccessToken(String refreshToken) {
        if (!jwtProvider.isTokenValid(refreshToken, REFRESH)) {
            throw new CommonUnauthorizedException();
        }

        Integer sellerId = jwtProvider.extractSellerId(refreshToken, REFRESH);

        sellerService.findSeller(sellerId);

        return new TokenDto.TokenRes(
            jwtProvider.generateToken(sellerId, ACCESS)
        );
    }
}
