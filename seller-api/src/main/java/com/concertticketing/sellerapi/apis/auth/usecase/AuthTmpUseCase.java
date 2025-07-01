package com.concertticketing.sellerapi.apis.auth.usecase;

import static com.concertticketing.sellerapi.security.provider.JwtProvider.TokenType.*;

import com.concertticketing.sellerapi.apis.auth.dto.TmpSignInDto.TmpSignInRes;
import com.concertticketing.sellerapi.common.annotation.UseCase;
import com.concertticketing.sellerapi.security.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AuthTmpUseCase {
    private final JwtProvider jwtProvider;

    public TmpSignInRes generateAccessAndRefreshTokens(Integer sellerId) {
        return new TmpSignInRes(
            jwtProvider.generateToken(sellerId, ACCESS),
            jwtProvider.generateToken(sellerId, REFRESH)
        );
    }
}
