package com.concertticketing.sellerapi.apis.auth.facade;

import static com.concertticketing.sellerapi.security.provider.JwtProvider.TokenType.*;

import com.concertticketing.sellerapi.apis.auth.dto.TmpSignInDto;
import com.concertticketing.sellerapi.common.annotation.Facade;
import com.concertticketing.sellerapi.security.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class AuthTmpFacade {
    private final JwtProvider jwtProvider;

    public TmpSignInDto.TmpSignInRes generateAccessAndRefreshTokens(Integer sellerId) {
        return new TmpSignInDto.TmpSignInRes(
            jwtProvider.generateToken(sellerId, ACCESS),
            jwtProvider.generateToken(sellerId, REFRESH)
        );
    }
}
