package com.concertticketing.schedulercore.domain.concert.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;
import com.concertticketing.domainredis.domain.concert.repository.ConcertQueueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheSearchService {
    private final ConcertCacheRepository concertCacheRepository;

    private final ConcertQueueRepository concertQueueRepository;

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
}
