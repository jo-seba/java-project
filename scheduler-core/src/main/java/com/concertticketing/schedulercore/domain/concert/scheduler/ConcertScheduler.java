package com.concertticketing.schedulercore.domain.concert.scheduler;

import static com.concertticketing.commonutils.TimeUtils.*;
import static com.concertticketing.domainredis.common.constant.QueueRedisTTL.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.schedulercore.domain.concert.dto.ConcertTicketingConfigDto;
import com.concertticketing.schedulercore.domain.concert.mapper.ConcertMapper;
import com.concertticketing.schedulercore.domain.concert.service.ConcertCacheService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertLocalCacheService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertTicketingConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertScheduler {
    private final ConcertMapper concertMapper;

    private final ConcertLocalCacheService concertLocalCacheService;

    private final ConcertCacheService concertCacheService;

    private final ConcertService concertService;

    private final ConcertTicketingConfigService concertTicketingConfigService;

    private final int WAITING_TO_ACTIVE_COUNT = 128;

    @Scheduled(cron = "00 50 23 * * *")
    public void putNextBookableConcertToLocalCache() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        concertLocalCacheService.putBookableConcerts(
            targetDate,
            concertService.findBookableConcerts(targetDate)
        );
    }

    @Scheduled(cron = "00 51 23 * * *")
    public void putNextExclusiveConcertToLocalCache() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        concertLocalCacheService.putExclusiveConcert(
            targetDate,
            concertMapper.toConcertTicketingConfigDto(
                concertTicketingConfigService
                    .findConcertTicketingConfig(targetDate)
                    .orElse(null)
            )
        );
    }

    @Scheduled(fixedDelay = 5000, zone = "Asia/Seoul")
    public void expireTokens() {
        log.info("expire tokens");
        LocalDateTime now = LocalDateTime.now();
        long expiredAt = localDateTimeToLong(now.minus(CONCERT_ACTIVE_TOKENS_TTL));
        Set<String> activeTokens = concertCacheService.getConcertActiveTokens(
            expiredAt
        );

        if (!activeTokens.isEmpty()) {
            concertCacheService.removeConcertActiveTokens(activeTokens);
            concertCacheService.unlinkConcertTicketUsers(activeTokens);
        }
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 10000, zone = "Asia/Seoul")
    public void expireExclusiveTokens() {
        log.info("expire exclusive tokens");
        LocalDateTime now = LocalDateTime.now();
        long waitingTokenExpiredAt = localDateTimeToLong(now.minus(CONCERT_WAITING_TOKENS_TTL));
        long activeTokenExpiredAt = localDateTimeToLong(now.minus(CONCERT_ACTIVE_TOKENS_TTL));

        try {
            ConcertTicketingConfigDto concert = concertLocalCacheService.getExclusiveConcert(now.toLocalDate())
                .orElseThrow(CommonNotFoundException::new);
            Set<String> waitingTokens = concertCacheService.getConcertWaitingTokenHeartbeats(
                concert.id(),
                waitingTokenExpiredAt
            );
            if (!waitingTokens.isEmpty()) {
                concertCacheService.removeConcertWaitingTokens(concert.id(), waitingTokens);
                concertCacheService.removeConcertWaitingTokenHeartbeats(concert.id(), waitingTokens);
            }

            Set<String> activeTokens = concertCacheService.getConcertActiveTokens(
                concert.id(),
                activeTokenExpiredAt
            );
            if (!activeTokens.isEmpty()) {
                concertCacheService.removeConcertActiveTokens(concert.id(), activeTokens);
            }
        } catch (CommonNotFoundException e) {
        }
    }

    public void moveExclusiveWaitingTokensToActiveTokens() {
        log.info("move exclusive waiting tokens to active tokens");
        LocalDateTime now = LocalDateTime.now();
        long minScore = localDateTimeToLong(now.minus(CONCERT_WAITING_TOKENS_TTL));
        long maxScore = localDateTimeToLong(now);
        try {
            ConcertTicketingConfigDto concert = concertLocalCacheService.getExclusiveConcert(now.toLocalDate())
                .orElseThrow(CommonNotFoundException::new);
            List<String> waitingTokens = concertCacheService.getConcertWaitingTokens(
                concert.id(),
                minScore,
                maxScore,
                WAITING_TO_ACTIVE_COUNT
            );
            if (CollectionUtils.isEmpty(waitingTokens)) {
                return;
            }

            concertCacheService.addConcertActiveTokens(
                concert.id(),
                localDateTimeToLong(now),
                waitingTokens
            );
            concertCacheService.removeConcertWaitingTokens(
                concert.id(),
                waitingTokens
            );

            String lastToken = waitingTokens.get(waitingTokens.size() - 1);
            concertCacheService.getConcertTokenUser(lastToken)
                .ifPresent(concertTokenUser -> {
                    concertCacheService.setConcertLastWaitingCount(
                        concert.id(),
                        concertTokenUser.lastWaitingCount()
                    );
                });
        } catch (CommonNotFoundException nfe) {
        } catch (Exception e) {
            log.error("moveExclusiveWaitingTokensToActiveTokens", e);
        }
    }
}
