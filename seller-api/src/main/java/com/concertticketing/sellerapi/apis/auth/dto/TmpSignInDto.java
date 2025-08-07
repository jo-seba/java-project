package com.concertticketing.sellerapi.apis.auth.dto;

import jakarta.validation.constraints.Min;

public final class TmpSignInDto {
    public record TmpSignInBody(
        @Min(1)
        Integer id
    ) {
    }

    public record TmpSignInRes(
        String accessToken,
        String refreshToken
    ) {
    }
}
