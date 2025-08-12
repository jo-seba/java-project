package com.concertticketing.userapi.apis.concerts.facade;

import static com.concertticketing.commonkafka.KafkaTopic.*;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.commonavro.ConcertSeatReservationRequestedEvent;
import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.domainredis.common.constant.ConcertSeatReservationStatus;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.userapi.apis.concerts.dto.HoldConcertSeatDto;
import com.concertticketing.userapi.apis.concerts.dto.HoldConcertSeatDto.HoldConcertSeatReq;
import com.concertticketing.userapi.apis.concerts.dto.PerformConcertSeatPaymentDto.PerformConcertSeatPaymentReq;
import com.concertticketing.userapi.apis.concerts.dto.PerformConcertSeatPaymentDto.PerformConcertSeatPaymentRes;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertSeatMapper;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheService;
import com.concertticketing.userapi.apis.concerts.service.ConcertSeatService;
import com.concertticketing.userapi.apis.concerts.service.ConcertService;
import com.concertticketing.userapi.common.annotation.Facade;
import com.concertticketing.userapi.common.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ConcertSeatFacade {
    private final KafkaProducer kafkaProducer;

    private final ConcertMapper concertMapper;
    private final ConcertSeatMapper concertSeatMapper;

    private final ConcertService concertService;

    private final ConcertCacheService concertCacheService;

    private final ConcertSeatService concertSeatService;

    private final int CONCERT_SEAT_RESERVATION_REQUESTED_MAX_RETRY = 3;

    @Transactional
    public HoldConcertSeatDto.HoldConcertSeatRes holdConcertSeat(
        Long userId,
        Long concertId,
        HoldConcertSeatReq body
    ) {
        ConcertTicketingCache concertTicketingCache = concertCacheService
            .getConcertTicketing(concertId)
            .orElseGet(() -> {
                ConcertTicketingCache newCachedConcert = concertMapper.toConcertTicketingCache(
                    concertService.findConcertTicketingInfo(concertId)
                );
                concertCacheService.setConcertTicketing(concertId, newCachedConcert);
                return newCachedConcert;
            });
        ;

        if (!concertTicketingCache.isBookingActive()) {
            throw new CommonBadRequestException();
        }

        boolean isSuccess = concertSeatService.reserveConcertSeat(
            concertId,
            userId,
            body.scheduleId(),
            body.areaId(),
            body.seatRow(),
            body.seatColumn()
        );

        return new HoldConcertSeatDto.HoldConcertSeatRes(isSuccess);
    }

    @Transactional
    public PerformConcertSeatPaymentRes performConcertSeatPayment(
        Long userId,
        Long concertId,
        PerformConcertSeatPaymentReq body
    ) {
        boolean isSuccess = concertSeatService.ongoingConcertSeat(
            concertId,
            userId,
            body.scheduleId(),
            body.areaId(),
            body.seatRow(),
            body.seatColumn()
        );

        String eventId = userId + "__" + UUID.randomUUID();
        concertCacheService.setConcertSeatReservation(
            concertId,
            userId,
            body.scheduleId(),
            concertSeatMapper.toConcertSeatReservationCache(
                eventId,
                concertId,
                userId,
                body,
                ConcertSeatReservationStatus.WAITING
            )
        );

        kafkaProducer.send(
            CONCERT_SEAT_RESERVATION_REQUESTED,
            new ConcertSeatReservationRequestedEvent(
                eventId,
                concertId,
                userId,
                body.scheduleId(),
                0,
                CONCERT_SEAT_RESERVATION_REQUESTED_MAX_RETRY
            )
        );
        
        return new PerformConcertSeatPaymentRes(isSuccess);
    }
}
