package com.concertticketing.kafkaconsumer.domain.concert.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertSeat;
import com.concertticketing.domainrdb.domain.concert.enums.SeatStatus;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertSeatService {
    private final ConcertSeatRepository concertSeatRepository;

    public Optional<ConcertSeat> findConcert(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn
    ) {
        return concertSeatRepository.findConcert(
            concertId,
            userId,
            scheduleId,
            areaId,
            seatRow,
            seatColumn
        );
    }

    public boolean updateConcertSeatStatus(
        Long id,
        SeatStatus status
    ) {
        return concertSeatRepository.updateConcertSeatStatus(id, status) == 1L;
    }
}
