package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.UserTokenExpiredEvent;
import com.concertticketing.kafkaconsumer.common.handler.auto.ConsumerAutoHandler;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveConcertOldTokensHandler
    extends ConsumerAutoHandler<UserTokenExpiredEvent> {
    private final ConcertCacheService concertCacheService;

    @Override
    @KafkaListener(
        topics = {CONCERT_USER_TOKEN_EXPIRED},
        groupId = REMOVE_CONCERT_OLD_TOKENS,
        containerFactory = SINGLE_AUTO_ACK_CONSUMER_FACTORY
    )
    public void consume(UserTokenExpiredEvent event) {
        handleEvent(event);
    }

    @Override
    public void handler(UserTokenExpiredEvent event) {
        List<String> oldTokens = concertCacheService.removeUserConcertOldTokens(
            event.getConcertId(),
            event.getUserId()
        );
        concertCacheService.removeConcertActiveTokens(oldTokens);
    }
}
