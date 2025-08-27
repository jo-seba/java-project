package com.concertticketing.sellerapi.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties("jwt")
@Validated
public record JwtProperty(
    @NotBlank
    String accessTokenSecretKey,
    @NotBlank
    String refreshTokenSecretKey,
    @Min(60 * 1000)
    long accessTokenExpire,
    @Min(10 * 60 * 1000)
    long refreshTokenExpire
) {
}
