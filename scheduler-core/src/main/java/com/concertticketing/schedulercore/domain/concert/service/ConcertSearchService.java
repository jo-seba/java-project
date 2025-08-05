package com.concertticketing.schedulercore.domain.concert.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.schedulercore.domain.concert.domain.Concert;
import com.concertticketing.schedulercore.domain.concert.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertSearchService {
    private final ConcertRepository concertRepository;

    public List<Concert> findBookableConcerts(LocalDate targetDate) {
        return concertRepository.findBookableConcerts(
            targetDate.atStartOfDay(),
            targetDate.atStartOfDay().plusDays(1)
        );
    }
}
