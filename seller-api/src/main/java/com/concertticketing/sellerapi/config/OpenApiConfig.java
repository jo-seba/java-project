package com.concertticketing.sellerapi.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {
    private Info apiInfo() {
        return new Info()
            .title("User Concert API")
            .description("유저 콘서트 API")
            .version("v1.0.0");
    }
}