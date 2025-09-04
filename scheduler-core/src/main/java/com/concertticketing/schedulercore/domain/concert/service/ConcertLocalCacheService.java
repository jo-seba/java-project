package com.concertticketing.schedulercore.domain.concert.service;

import static com.concertticketing.schedulercore.common.cache.constant.LocalCacheKey.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.schedulercore.common.cache.LocalCacheManager;
import com.concertticketing.schedulercore.common.cache.LocalCacheType;
import com.concertticketing.schedulercore.domain.concert.dto.BookableConcertDto;
import com.concertticketing.schedulercore.domain.concert.dto.ConcertTicketingConfigDto;
import com.concertticketing.schedulercore.domain.concert.mapper.ConcertMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertLocalCacheService {
    private final LocalCacheManager localCacheManager;

    private final ConcertMapper concertMapper;

    public void putBookableConcerts(LocalDate date, List<Concert> concerts) {
        localCacheManager.put(
            LocalCacheType.BOOKABLE_CONCERTS,
            bookableConcertsKey(date),
            concerts.stream()
                .map(concertMapper::toBookableConcertDto)
                .collect(Collectors.toList())
        );
    }

    public List<BookableConcertDto> getBookableConcerts(LocalDate date) {
        return localCacheManager.get(
            LocalCacheType.BOOKABLE_CONCERTS,
            bookableConcertsKey(date)
        );
    }

    public void putExclusiveConcert(LocalDate date, ConcertTicketingConfigDto concert) {
        localCacheManager.put(
            LocalCacheType.EXCLUSIVE_CONCERT,
            exclusiveConcertKey(date),
            concert
        );
    }

    public Optional<ConcertTicketingConfigDto> getExclusiveConcert(LocalDate date) {
        return localCacheManager.get(
            LocalCacheType.EXCLUSIVE_CONCERT,
            exclusiveConcertKey(date),
            ConcertTicketingConfigDto.class
        );
    }
}
