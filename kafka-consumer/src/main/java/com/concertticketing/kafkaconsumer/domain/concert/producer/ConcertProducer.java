package com.concertticketing.kafkaconsumer.domain.concert.producer;

import static com.concertticketing.commonkafka.KafkaTopic.*;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.ExclusiveUserTokenExpiredEvent;
import com.concertticketing.commonavro.UserTokenExpiredEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConcertProducer {
    private final KafkaTemplate<String, UserTokenExpiredEvent> userTokenExpiredKafkaTemplate;
    private final KafkaTemplate<String, ExclusiveUserTokenExpiredEvent> exclusiveUserTokenExpiredKafkaTemplate;

    public void sendConcertUserTokenExpired(UserTokenExpiredEvent event) {
        userTokenExpiredKafkaTemplate.send(CONCERT_USER_TOKEN_EXPIRED, event);
    }

    public void sendConcertExclusiveUserTokenExpired(ExclusiveUserTokenExpiredEvent event) {
        exclusiveUserTokenExpiredKafkaTemplate.send(CONCERT_EXCLUSIVE_USER_TOKEN_EXPIRED, event);
    }
}
