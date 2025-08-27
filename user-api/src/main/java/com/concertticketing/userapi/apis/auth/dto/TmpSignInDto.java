package com.concertticketing.userapi.apis.auth.dto;

import jakarta.validation.constraints.Min;

public final class TmpSignInDto {
    public record TmpSignInBody(
        @Min(1)
        Long id
    ) {
    }

    public record TmpSignInRes(
        String accessToken,
        String refreshToken
    ) {
    }
}

