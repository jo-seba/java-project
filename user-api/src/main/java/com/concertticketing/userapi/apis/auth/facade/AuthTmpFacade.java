package com.concertticketing.userapi.apis.auth.facade;

import static com.concertticketing.userapi.security.provider.JwtProvider.TokenType.*;

import com.concertticketing.userapi.apis.auth.dto.TmpSignInDto;
import com.concertticketing.userapi.common.annotation.Facade;
import com.concertticketing.userapi.security.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class AuthTmpFacade {
    private final JwtProvider jwtProvider;

    public TmpSignInDto.TmpSignInRes generateAccessAndRefreshTokens(Long userId) {
        return new TmpSignInDto.TmpSignInRes(
            jwtProvider.generateToken(userId, ACCESS),
            jwtProvider.generateToken(userId, REFRESH)
        );
    }
}
