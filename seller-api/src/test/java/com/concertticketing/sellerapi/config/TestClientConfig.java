package com.concertticketing.sellerapi.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.concertticketing.sellerapi.common.client.TestClient;
import com.concertticketing.sellerapi.security.provider.JwtProvider;

@TestConfiguration
public class TestClientConfig {
    @Bean
    @Scope("prototype")
    TestClient testClient(
        Environment environment,
        JwtProvider jwtProvider
    ) {
        return new TestClient(environment, jwtProvider);
    }
}
