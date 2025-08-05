package com.concertticketing.userapi.apis.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.userapi.apis.auth.dto.TmpSignInDto.TmpSignInBody;
import com.concertticketing.userapi.apis.auth.dto.TmpSignInDto.TmpSignInRes;
import com.concertticketing.userapi.apis.auth.facade.AuthTmpFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/tmp")
public class AuthTmpController {
    private final AuthTmpFacade authTmpFacade;

    @PostMapping("/sign-in")
    public ResponseEntity<TmpSignInRes> tmpSignIn(
        @RequestBody @Validated TmpSignInBody body
    ) {
        return ResponseEntity.ok(
            authTmpFacade.generateAccessAndRefreshTokens(body.id())
        );
    }
}
