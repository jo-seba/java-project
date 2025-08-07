package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.domainredis.common.constant.ConcertEntryStatus.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.UserJoinedWaitingQueueEvent;
import com.concertticketing.commonavro.UserTokenExpiredEvent;
import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainredis.common.constant.ConcertEntryStatus;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.kafkaconsumer.common.handler.auto.ConsumerAutoHandler;
import com.concertticketing.kafkaconsumer.domain.concert.producer.ConcertProducer;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddConcertUserToActiveQueueHandler
    extends ConsumerAutoHandler<UserJoinedWaitingQueueEvent> {
    private final ConcertProducer concertProducer;

    private final ConcertCacheService concertCacheService;

    @Override
    @KafkaListener(
        topics = {CONCERT_USER_JOINED_WAITING_QUEUE},
        groupId = ADD_CONCERT_USER_TO_ACTIVE_QUEUE,
        containerFactory = SINGLE_AUTO_ACK_CONSUMER_FACTORY
    )
    public void consume(UserJoinedWaitingQueueEvent event) {
        handleEvent(event);
    }

    @Override
    public void handler(UserJoinedWaitingQueueEvent event) {
        ConcertTokenUserCache concertTokenUser = concertCacheService.getConcertTokenUser(
            event.getToken()
        ).orElseThrow(CommonNotFoundException::new);

        if (concertTokenUser.status() == ALLOWED) {
            return;
        }

        if (Boolean.FALSE.equals(
            concertCacheService.addConcertActiveTokenNX(event.getToken(), event.getCreatedAt())
        )) {
            throw new CommonConflictException();
        }

        // 여러번 입장 시 기존 토큰 만료 처리
        if (concertCacheService.leftPushConcertUserToken(
            event.getConcertId(),
            event.getUserId(),
            event.getToken()
        ) > 1L) {
            concertProducer.sendConcertUserTokenExpired(new UserTokenExpiredEvent(
                event.getUserId(),
                event.getConcertId()
            ));
        }

        concertCacheService.setConcertTokenUser(
            event.getToken(),
            concertTokenUser.withStatus(ConcertEntryStatus.ALLOWED)
        );
    }
}
