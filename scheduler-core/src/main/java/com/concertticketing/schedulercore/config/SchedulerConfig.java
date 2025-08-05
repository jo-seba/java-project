package com.concertticketing.schedulercore.config;

import static com.concertticketing.commonutils.TimeUtils.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.concertticketing.schedulercore.common.cache.LocalCacheInitStatus;
import com.concertticketing.schedulercore.domain.concert.scheduler.ConcertScheduler;
import com.concertticketing.schedulercore.domain.concert.service.ConcertCacheSearchService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertLocalCacheService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig implements SchedulingConfigurer {
    private final int POOL_SIZE = 3;
    private final long THIRTY_SECONDS_MILLIS = Duration.ofSeconds(30).toMillis();
    private final long FIVE_SECONDS_MILLIS = Duration.ofSeconds(5).toMillis();
    private final long THREE_SECONDS_MILLIS = Duration.ofSeconds(3).toMillis();

    private final LocalCacheInitStatus localCacheInitStatus;

    private final ConcertScheduler concertScheduler;

    private final ConcertLocalCacheService concertLocalCacheService;

    private final ConcertCacheSearchService concertCacheSearchService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.setThreadNamePrefix("scheduler-task-");
        scheduler.initialize();

        taskRegistrar.setTaskScheduler(scheduler);
        taskRegistrar.addTriggerTask(
            concertScheduler::moveExclusiveWaitingTokensToActiveTokens,
            context ->
                Optional.ofNullable(context.lastCompletion())
                    .map(last -> last.plusMillis(getMoveExclusiveTokenDelay(last)))
                    .orElse(Instant.now())
        );
    }

    private long getMoveExclusiveTokenDelay(Instant lastCompletionTime) {
        LocalDateTime last = instantToLocalDateTime(lastCompletionTime);

        return concertLocalCacheService
            .getExclusiveConcert(last.toLocalDate())
            .map(concert -> {
                long millisUntilBooking = Duration
                    .between(last, concert.startedAt())
                    .toMillis();

                if (millisUntilBooking > THIRTY_SECONDS_MILLIS) {
                    return millisUntilBooking - THIRTY_SECONDS_MILLIS;
                } else if (millisUntilBooking >= 0) {
                    return FIVE_SECONDS_MILLIS;
                } else {
                    return concertCacheSearchService
                        .getConcertActiveTokenCount(concert.id()) > concert.capacity()
                        ? FIVE_SECONDS_MILLIS : THREE_SECONDS_MILLIS;
                }
            })
            .orElseGet(() ->
                localCacheInitStatus.isConcertLocalCacheReady()
                    ? Duration
                    .between(last, last.toLocalDate().plusDays(1).atStartOfDay())
                    .toMillis()
                    : FIVE_SECONDS_MILLIS
            );
    }
}
