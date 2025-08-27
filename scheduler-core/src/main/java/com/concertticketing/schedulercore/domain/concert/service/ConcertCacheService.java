package com.concertticketing.schedulercore.domain.concert.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;
import com.concertticketing.domainredis.domain.concert.repository.ConcertQueueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheService {
    private final ConcertCacheRepository concertCacheRepository;

    private final ConcertQueueRepository concertQueueRepository;

    // Create
    public Long addConcertActiveTokens(Long concertId, long timestamp, List<String> tokens) {
        return concertQueueRepository.addConcertActiveTokens(concertId, timestamp, tokens);
    }

    public void setConcertLastWaitingCount(Long concertId, Long lastWaitingCount) {
        concertCacheRepository.setConcertLastWaitingCount(concertId, lastWaitingCount);
    }

    // Read
    public Optional<ConcertTokenUserCache> getConcertTokenUser(String token) {
        return concertCacheRepository.getConcertTokenUser(token);
    }

    public Set<String> getConcertActiveTokens(long maxScore) {
        return concertQueueRepository.getConcertActiveTokens(maxScore);
    }

    public Long getConcertActiveTokenCount(Long concertId) {
        return concertQueueRepository.getConcertActiveTokenCount(concertId);
    }

    public List<String> getConcertWaitingTokens(Long concertId, long minScore, long maxScore, long limit) {
        return concertQueueRepository.getConcertWaitingTokens(concertId, minScore, maxScore, limit);
    }

    public Set<String> getConcertActiveTokens(Long concertId, long maxScore) {
        return concertQueueRepository.getConcertActiveTokens(concertId, maxScore);
    }

    public Set<String> getConcertWaitingTokenHeartbeats(Long concertId, long maxScore) {
        return concertQueueRepository.getConcertWaitingTokenHeartbeats(concertId, maxScore);
    }

    // Update

    // Delete
    public Long unlinkConcertTicketUsers(Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertCacheRepository.unlinkConcertTokenUsers(tokens);
    }

    public Long removeConcertActiveTokens(Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertQueueRepository.removeConcertActiveTokens(tokens.toArray());
    }

    public Long removeConcertWaitingTokens(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokens(concertId, tokens.toArray());
    }

    public Long removeConcertWaitingTokens(Long concertId, Set<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokens(concertId, tokens.toArray());
    }

    public Long removeConcertActiveTokens(Long concertId, Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertQueueRepository.removeConcertActiveTokens(concertId, tokens.toArray());
    }

    public Long removeConcertWaitingTokenHeartbeats(Long concertId, Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertQueueRepository.removeConcertWaitingTokenHeartbeats(concertId, tokens.toArray());
    }
}
