package com.concertticketing.kafkaconsumer.domain.concert.consumer;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.domainredis.common.constant.ConcertEntryStatus.*;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.ExclusiveUserTokenExpiredEvent;
import com.concertticketing.commonavro.UserJoinedExclusiveQueueEvent;
import com.concertticketing.commonavro.UserJoinedWaitingQueueEvent;
import com.concertticketing.commonavro.UserTokenExpiredEvent;
import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainredis.common.constant.ConcertEntryStatus;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.kafkaconsumer.domain.concert.producer.ConcertProducer;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertConsumer {
    private final ConcertProducer concertProducer;

    private final ConcertCacheService concertCacheService;

    @KafkaListener(topics = {CONCERT_USER_JOINED_WAITING_QUEUE}, groupId = "add-concert-user-to-active-queue")
    public void addConcertUserToActiveQueue(UserJoinedWaitingQueueEvent event) {
        try {
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
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(
        topics = {CONCERT_USER_JOINED_EXCLUSIVE_QUEUE},
        groupId = "add-concert-user-to-exclusive-waiting-queue"
    )
    public void addConcertUserToExclusiveWaitingQueue(UserJoinedExclusiveQueueEvent event) {
        try {
            ConcertTokenUserCache concertTokenUser = concertCacheService.getConcertTokenUser(
                event.getToken()
            ).orElseThrow(CommonNotFoundException::new);

            if (concertTokenUser.status() == ALLOWED) {
                return;
            }

            if (Boolean.FALSE.equals(
                concertCacheService.addConcertWaitingTokenNX(
                    event.getConcertId(),
                    event.getToken(),
                    event.getCreatedAt()
                )
            )) {
                throw new CommonConflictException();
            }

            // 여러번 입장 시 기존 토큰 만료 처리
            if (concertCacheService.leftPushConcertUserToken(
                event.getConcertId(),
                event.getUserId(),
                event.getToken()
            ) > 1L) {
                concertProducer.sendConcertExclusiveUserTokenExpired(new ExclusiveUserTokenExpiredEvent(
                    event.getUserId(),
                    event.getConcertId()
                ));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(
        topics = {CONCERT_USER_JOINED_EXCLUSIVE_QUEUE},
        groupId = "add-exclusive-concert-token-to-heartbeat"
    )
    public void addExclusiveConcertTokenToHeartbeat(UserJoinedExclusiveQueueEvent event) {
        try {
            concertCacheService.addConcertWaitingTokenHeartbeat(
                event.getConcertId(),
                event.getToken(),
                event.getCreatedAt()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = {CONCERT_USER_TOKEN_EXPIRED}, groupId = "remove-concert-old-tokens")
    public void removeConcertOldTokens(UserTokenExpiredEvent event) {
        try {
            List<String> oldTokens = concertCacheService.removeUserConcertOldTokens(
                event.getConcertId(),
                event.getUserId()
            );
            concertCacheService.removeConcertActiveTokens(oldTokens);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = {CONCERT_EXCLUSIVE_USER_TOKEN_EXPIRED}, groupId = "remove-concert-old-exclusive-tokens")
    public void removeConcertOldExclusiveTokens(ExclusiveUserTokenExpiredEvent event) {
        try {
            List<String> oldTokens = concertCacheService.removeUserConcertOldTokens(
                event.getConcertId(),
                event.getUserId()
            );
            concertCacheService.removeConcertActiveTokens(event.getConcertId(), oldTokens);
            concertCacheService.removeConcertWaitingTokens(event.getConcertId(), oldTokens);

            concertCacheService.removeConcertWaitingTokenHeartbeats(event.getConcertId(), oldTokens);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
