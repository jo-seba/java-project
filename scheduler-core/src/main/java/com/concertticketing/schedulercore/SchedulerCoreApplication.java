package com.concertticketing.schedulercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {
    "com.concertticketing.schedulercore",
    "com.concertticketing.domainredis"
})
@ConfigurationPropertiesScan(basePackages = {
    "com.concertticketing.domainredis.common.property"
})
public class SchedulerCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerCoreApplication.class, args);
    }

}
