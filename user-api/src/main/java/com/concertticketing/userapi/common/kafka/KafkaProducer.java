package com.concertticketing.userapi.common.kafka;

import static com.concertticketing.commonkafka.KafkaTopic.*;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.UserJoinedExclusiveQueueEvent;
import com.concertticketing.commonavro.UserJoinedWaitingQueueEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, UserJoinedWaitingQueueEvent> userJoinedWaitingKafkaTemplate;
    private final KafkaTemplate<String, UserJoinedExclusiveQueueEvent> userJoinedExclusiveKafkaTemplate;

    public void sendConcertUserJoinedWaitingQueue(UserJoinedWaitingQueueEvent event) {
        userJoinedWaitingKafkaTemplate.send(CONCERT_USER_JOINED_WAITING_QUEUE, event);
    }

    public void sendConcertUserJoinedExclusiveQueue(UserJoinedExclusiveQueueEvent event) {
        userJoinedExclusiveKafkaTemplate.send(CONCERT_USER_JOINED_EXCLUSIVE_QUEUE, event);
    }
}
