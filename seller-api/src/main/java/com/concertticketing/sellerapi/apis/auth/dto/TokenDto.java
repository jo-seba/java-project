package com.concertticketing.sellerapi.apis.auth.dto;

import jakarta.validation.constraints.NotBlank;

public final class TokenDto {
    public record TokenBody(
        @NotBlank
        String refreshToken
    ) {
    }

    public record TokenRes(
        String accessToken
    ) {
    }
}
