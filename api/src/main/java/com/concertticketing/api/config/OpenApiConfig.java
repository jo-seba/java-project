package com.concertticketing.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

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