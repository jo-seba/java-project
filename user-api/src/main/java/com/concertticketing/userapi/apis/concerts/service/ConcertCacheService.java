package com.concertticketing.userapi.apis.concerts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.domain.ConcertListCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertSeatReservationCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;
import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheService {
    private final ConcertCacheRepository concertCacheRepository;

    // Create
    public void setConcertTicketing(Long concertId, ConcertTicketingCache concertTicketing) {
        concertCacheRepository.setConcertTicketing(concertId, concertTicketing);
    }

    public Boolean setConcertTokenUserNX(String token, ConcertTokenUserCache concertTokenUser) {
        return concertCacheRepository.setConcertTokenUserNX(token, concertTokenUser);
    }

    public void setConcertSeatReservation(
        Long concertId,
        Long userId,
        Long scheduleId,
        ConcertSeatReservationCache value
    ) {
        concertCacheRepository.setConcertSeatReservation(
            concertId,
            userId,
            scheduleId,
            value
        );
    }

    // Read
    public Optional<ConcertTicketingCache> getConcertTicketing(Long id) {
        return concertCacheRepository.getConcertTicketing(id);
    }

    public Optional<ConcertTokenUserCache> getConcertTokenUser(String token) {
        return concertCacheRepository.getConcertTokenUser(token);
    }

    public Long getConcertLastWaitingCount(Long concertId) {
        return concertCacheRepository.getConcertLastWaitingCount(concertId);
    }

    public List<ConcertListCache> getConcerts(ConcertSort sort) {
        return concertCacheRepository.getConcerts(sort.toString());
    }

    // Update
    public Long incrConcertWaitingCount(Long concertId) {
        return concertCacheRepository.incrConcertWaitingCount(concertId);
    }

    // Delete
    public Optional<String> popConcertOpaqueToken() {
        return concertCacheRepository.leftPopConcertOpaqueToken();
    }
}
