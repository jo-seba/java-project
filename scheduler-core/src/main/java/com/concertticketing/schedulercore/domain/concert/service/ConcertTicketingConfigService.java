package com.concertticketing.schedulercore.domain.concert.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertTicketingConfig;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertTicketingConfigRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertTicketingConfigService {
    private final ConcertTicketingConfigRepository concertTicketingConfigRepository;

    @Transactional(readOnly = true)
    public Optional<ConcertTicketingConfig> findConcertTicketingConfig(LocalDate targetDate) {
        return concertTicketingConfigRepository.findConcertTicketingQueueConfig(
            targetDate.atStartOfDay(),
            targetDate.atStartOfDay().plusDays(1)
        );
    }
}
