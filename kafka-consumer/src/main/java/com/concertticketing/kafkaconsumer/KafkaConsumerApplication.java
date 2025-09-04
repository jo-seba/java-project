package com.concertticketing.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.concertticketing.kafkaconsumer",
    "com.concertticketing.domainredis",
    "com.concertticketing.domainrdb"
})
@ConfigurationPropertiesScan(basePackages = {
    "com.concertticketing.domainredis.common.property"
})
@EnableMongoRepositories(
    "com.concertticketing.domainmongodb.domain"
)
@EntityScan(basePackages = {
    "com.concertticketing.domainrdb.domain"
})
@EnableJpaRepositories(
    basePackages = "com.concertticketing.domainrdb.domain"
)
public class KafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }

}
