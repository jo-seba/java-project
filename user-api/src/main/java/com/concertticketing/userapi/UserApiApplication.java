package com.concertticketing.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.concertticketing.userapi",
    "com.concertticketing.domainredis",
    "com.concertticketing.domainrdb"
})
@ConfigurationPropertiesScan(basePackages = {
    "com.concertticketing.userapi.common.property",
    "com.concertticketing.domainredis.common.property"
})
@EntityScan(basePackages = {
    "com.concertticketing.domainrdb.domain"
})
@EnableJpaRepositories(
    basePackages = "com.concertticketing.domainrdb.domain"
)
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

}
