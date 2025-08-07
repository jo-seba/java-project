package com.concertticketing.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
    "com.concertticketing.kafkaconsumer",
    "com.concertticketing.domainredis"
})
@ConfigurationPropertiesScan(basePackages = {
    "com.concertticketing.domainredis.common.property"
})
public class KafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }

}
