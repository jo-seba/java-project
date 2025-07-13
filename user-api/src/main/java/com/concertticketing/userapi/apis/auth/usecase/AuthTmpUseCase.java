package com.concertticketing.userapi.apis.auth.usecase;

import static com.concertticketing.userapi.security.provider.JwtProvider.TokenType.*;

import com.concertticketing.userapi.apis.auth.dto.TmpSignInDto.TmpSignInRes;
import com.concertticketing.userapi.common.annotation.UseCase;
import com.concertticketing.userapi.security.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AuthTmpUseCase {
    private final JwtProvider jwtProvider;

    public TmpSignInRes generateAccessAndRefreshTokens(Long userId) {
        return new TmpSignInRes(
            jwtProvider.generateToken(userId, ACCESS),
            jwtProvider.generateToken(userId, REFRESH)
        );
    }
}
