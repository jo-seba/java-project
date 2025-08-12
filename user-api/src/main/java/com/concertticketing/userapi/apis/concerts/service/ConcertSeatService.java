package com.concertticketing.userapi.apis.concerts.service;

import static com.concertticketing.commonutils.TimeUtils.*;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.userapi.apis.concerts.repository.ConcertSeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertSeatService {
    private final ConcertSeatRepository concertSeatRepository;

    private final Duration holdDuration = Duration.ofMinutes(10);

    @Transactional
    public boolean reserveConcertSeat(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn
    ) {
        return concertSeatRepository.reserveConcertSeat(
            concertId,
            userId,
            scheduleId,
            areaId,
            seatRow,
            seatColumn,
            now().plus(holdDuration)
        );
    }

    @Transactional
    public boolean ongoingConcertSeat(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn
    ) {
        return concertSeatRepository.ongoingConcertSeat(
            concertId,
            userId,
            scheduleId,
            areaId,
            seatRow,
            seatColumn
        );
    }
}
