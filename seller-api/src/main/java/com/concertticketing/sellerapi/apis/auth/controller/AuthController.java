package com.concertticketing.sellerapi.apis.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.sellerapi.apis.auth.dto.TokenDto.TokenBody;
import com.concertticketing.sellerapi.apis.auth.dto.TokenDto.TokenRes;
import com.concertticketing.sellerapi.apis.auth.facade.AuthFacade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthFacade authFacade;

    @PostMapping("/token")
    public ResponseEntity<TokenRes> newAccessToken(
        @RequestBody @Validated TokenBody body
    ) {
        return ResponseEntity.ok(authFacade.generateAccessToken(body.refreshToken()));
    }
}
