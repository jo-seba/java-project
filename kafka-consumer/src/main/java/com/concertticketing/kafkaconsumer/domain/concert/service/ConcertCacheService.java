package com.concertticketing.kafkaconsumer.domain.concert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.common.constant.ConcertPaymentEventStatus;
import com.concertticketing.domainredis.domain.concert.domain.ConcertListCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertSeatReservationCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;
import com.concertticketing.domainredis.domain.concert.repository.ConcertQueueRepository;
import com.concertticketing.kafkaconsumer.common.constant.ConcertSort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheService {
    private final ConcertCacheRepository concertCacheRepository;

    private final ConcertQueueRepository concertQueueRepository;

    // Create
    public void setConcertTokenUser(String token, ConcertTokenUserCache concertTokenUser) {
        concertCacheRepository.setConcertTokenUser(token, concertTokenUser);
    }

    public Boolean addConcertActiveTokenNX(String token, long createdAt) {
        return concertQueueRepository.addConcertActiveTokenNX(token, createdAt);
    }

    public Long leftPushConcertUserToken(Long concertId, Long userId, String token) {
        return concertQueueRepository.leftPushConcertUserToken(concertId, userId, token);
    }

    public Boolean addConcertWaitingTokenNX(Long concertId, String token, long createdAt) {
        return concertQueueRepository.addConcertWaitingTokenNX(concertId, token, createdAt);
    }

    public void addConcertWaitingTokenHeartbeat(Long concertId, String token, long timestamp) {
        concertQueueRepository.addConcertWaitingTokenHeartbeat(concertId, token, timestamp);
    }

    public void setConcerts(ConcertSort sort, List<ConcertListCache> concerts) {
        concertCacheRepository.setConcerts(sort.toString(), concerts);
    }

    // Read
    public Optional<ConcertTokenUserCache> getConcertTokenUser(String token) {
        return concertCacheRepository.getConcertTokenUser(token);
    }

    public Optional<ConcertSeatReservationCache> getConcertSeatReservation(
        Long concertId,
        Long userId,
        Long scheduleId
    ) {
        return concertCacheRepository.getConcertSeatReservation(concertId, userId, scheduleId);
    }

    // Update
    public boolean setConcertPaymentEventStatus(String eventId, ConcertPaymentEventStatus status) {
        return concertCacheRepository.setConcertPaymentEventStatus(eventId, status);
    }

    // Delete
    public Long removeConcertActiveTokens(List<String> tokens) {
        return concertQueueRepository.removeConcertActiveTokens(tokens.toArray());
    }

    public List<String> removeUserConcertOldTokens(Long concertId, Long userId) {
        return concertQueueRepository.retainFirstPopRestConcertUserToken(concertId, userId);
    }

    public Long removeConcertWaitingTokens(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokens(concertId, tokens.toArray());
    }

    public Long removeConcertActiveTokens(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertActiveTokens(concertId, tokens.toArray());
    }

    public Long removeConcertWaitingTokenHeartbeats(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokenHeartbeats(concertId, tokens.toArray());
    }
}
