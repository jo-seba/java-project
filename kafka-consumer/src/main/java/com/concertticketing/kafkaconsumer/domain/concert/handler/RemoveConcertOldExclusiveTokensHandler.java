package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.ExclusiveUserTokenExpiredEvent;
import com.concertticketing.kafkaconsumer.common.handler.auto.ConsumerAutoHandler;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveConcertOldExclusiveTokensHandler
    extends ConsumerAutoHandler<ExclusiveUserTokenExpiredEvent> {
    private final ConcertCacheService concertCacheService;

    @Override
    @KafkaListener(
        topics = {CONCERT_EXCLUSIVE_USER_TOKEN_EXPIRED},
        groupId = REMOVE_CONCERT_OLD_EXCLUSIVE_TOKENS,
        containerFactory = SINGLE_AUTO_ACK_CONSUMER_FACTORY
    )
    public void consume(ExclusiveUserTokenExpiredEvent event) {
        handleEvent(event);
    }

    @Override
    public void handler(ExclusiveUserTokenExpiredEvent event) {
        List<String> oldTokens = concertCacheService.removeUserConcertOldTokens(
            event.getConcertId(),
            event.getUserId()
        );
        concertCacheService.removeConcertActiveTokens(event.getConcertId(), oldTokens);
        concertCacheService.removeConcertWaitingTokens(event.getConcertId(), oldTokens);

        concertCacheService.removeConcertWaitingTokenHeartbeats(event.getConcertId(), oldTokens);
    }
}
