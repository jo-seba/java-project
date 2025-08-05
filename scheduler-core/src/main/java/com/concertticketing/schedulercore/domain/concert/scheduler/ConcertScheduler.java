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
import com.concertticketing.schedulercore.domain.concert.service.ConcertCacheCreateService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertCacheDeleteService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertCacheSearchService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertLocalCacheService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertSearchService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertTicketingConfigSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertScheduler {
    private final ConcertMapper concertMapper;

    private final ConcertLocalCacheService concertLocalCacheService;

    private final ConcertCacheCreateService concertCacheCreateService;
    private final ConcertCacheSearchService concertCacheSearchService;
    private final ConcertCacheDeleteService concertCacheDeleteService;

    private final ConcertSearchService concertSearchService;

    private final ConcertTicketingConfigSearchService concertTicketingConfigSearchService;

    private final int WAITING_TO_ACTIVE_COUNT = 128;

    @Scheduled(cron = "00 50 23 * * *")
    public void putNextBookableConcertToLocalCache() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        concertLocalCacheService.putBookableConcerts(
            targetDate,
            concertSearchService.findBookableConcerts(targetDate)
        );
    }

    @Scheduled(cron = "00 51 23 * * *")
    public void putNextExclusiveConcertToLocalCache() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        concertLocalCacheService.putExclusiveConcert(
            targetDate,
            concertMapper.toConcertTicketingConfigDto(
                concertTicketingConfigSearchService
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
        Set<String> activeTokens = concertCacheSearchService.getConcertActiveTokens(
            expiredAt
        );

        if (!activeTokens.isEmpty()) {
            concertCacheDeleteService.removeConcertActiveTokens(activeTokens);
            concertCacheDeleteService.unlinkConcertTicketUsers(activeTokens);
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
            Set<String> waitingTokens = concertCacheSearchService.getConcertWaitingTokenHeartbeats(
                concert.id(),
                waitingTokenExpiredAt
            );
            if (!waitingTokens.isEmpty()) {
                concertCacheDeleteService.removeConcertWaitingTokens(concert.id(), waitingTokens);
                concertCacheDeleteService.removeConcertWaitingTokenHeartbeats(concert.id(), waitingTokens);
            }

            Set<String> activeTokens = concertCacheSearchService.getConcertActiveTokens(
                concert.id(),
                activeTokenExpiredAt
            );
            if (!activeTokens.isEmpty()) {
                concertCacheDeleteService.removeConcertActiveTokens(concert.id(), activeTokens);
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
            List<String> waitingTokens = concertCacheSearchService.getConcertWaitingTokens(
                concert.id(),
                minScore,
                maxScore,
                WAITING_TO_ACTIVE_COUNT
            );
            if (CollectionUtils.isEmpty(waitingTokens)) {
                return;
            }

            concertCacheCreateService.addConcertActiveTokens(
                concert.id(),
                localDateTimeToLong(now),
                waitingTokens
            );
            concertCacheDeleteService.removeConcertWaitingTokens(
                concert.id(),
                waitingTokens
            );

            String lastToken = waitingTokens.get(waitingTokens.size() - 1);
            concertCacheSearchService.getConcertTokenUser(lastToken)
                .ifPresent(concertTokenUser -> {
                    concertCacheCreateService.setConcertLastWaitingCount(
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
