package com.concertticketing.sellerapi.apis.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.sellerapi.apis.auth.dto.TmpSignInDto.TmpSignInBody;
import com.concertticketing.sellerapi.apis.auth.dto.TmpSignInDto.TmpSignInRes;
import com.concertticketing.sellerapi.apis.auth.usecase.AuthTmpUseCase;
import com.concertticketing.sellerapi.common.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/tmp")
public class AuthTmpController {
    private final AuthTmpUseCase authTmpUseCase;

    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponse<TmpSignInRes>> tmpSignIn(
        @RequestBody @Validated TmpSignInBody body
    ) {
        return ResponseEntity.ok(
            SuccessResponse.from(authTmpUseCase.generateAccessAndRefreshTokens(body.id()))
        );
    }
}
