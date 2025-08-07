package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.UserJoinedExclusiveQueueEvent;
import com.concertticketing.kafkaconsumer.common.handler.auto.ConsumerAutoHandler;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddExclusiveConcertTokenToHeartbeatHandler
    extends ConsumerAutoHandler<UserJoinedExclusiveQueueEvent> {
    private final ConcertCacheService concertCacheService;

    @Override
    @KafkaListener(
        topics = {CONCERT_USER_JOINED_EXCLUSIVE_QUEUE},
        groupId = ADD_EXCLUSIVE_CONCERT_TOKEN_TO_HEARTBEAT,
        containerFactory = SINGLE_AUTO_ACK_CONSUMER_FACTORY
    )
    public void consume(UserJoinedExclusiveQueueEvent event) {
        handleEvent(event);
    }

    @Override
    public void handler(UserJoinedExclusiveQueueEvent event) {
        concertCacheService.addConcertWaitingTokenHeartbeat(
            event.getConcertId(),
            event.getToken(),
            event.getCreatedAt()
        );
    }
}
