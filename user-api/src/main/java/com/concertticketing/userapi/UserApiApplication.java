package com.concertticketing.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
    "com.concertticketing.userapi",
    "com.concertticketing.domainredis"
})
@ConfigurationPropertiesScan(basePackages = {
    "com.concertticketing.userapi.common.property",
    "com.concertticketing.domainredis.common.property"
})
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

}
