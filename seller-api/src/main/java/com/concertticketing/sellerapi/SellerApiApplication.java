package com.concertticketing.sellerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.concertticketing.sellerapi",
    "com.concertticketing.domainrdb"
})
@ConfigurationPropertiesScan
@EnableJpaRepositories(
    basePackages = "com.concertticketing.domainrdb.domain"
)
public class SellerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerApiApplication.class, args);
    }

}
