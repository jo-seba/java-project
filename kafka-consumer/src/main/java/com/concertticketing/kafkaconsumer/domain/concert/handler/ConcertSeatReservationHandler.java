package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.ConcertSeatReservationRequestedEvent;
import com.concertticketing.commonerror.exception.common.CommonInternalServerException;
import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainrdb.domain.concert.domain.ConcertSeat;
import com.concertticketing.domainrdb.domain.concert.enums.SeatStatus;
import com.concertticketing.domainredis.domain.concert.domain.ConcertSeatReservationCache;
import com.concertticketing.kafkaconsumer.common.handler.manual.ConsumerManualHandler;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertSeatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertSeatReservationHandler
    extends ConsumerManualHandler<ConcertSeatReservationRequestedEvent> {
    private final ConcertCacheService concertCacheService;

    private final ConcertSeatService concertSeatService;

    @Override
    @KafkaListener(
        topics = {CONCERT_SEAT_RESERVATION_DLQ},
        groupId = CONCERT_SEAT_RESERVATION_DLQ_REQUEST,
        containerFactory = SINGLE_MANUAL_ACK_CONSUMER_FACTORY
    )
    public void consume(ConcertSeatReservationRequestedEvent event, Acknowledgment ack) {
        try {
            handler(event, ack);
        } catch (Exception e) {
            log.error("Error processing ConcertSeatReservationRequestedEvent: {}", event.getEventId(), e);
        } finally {
            ack.acknowledge();
        }
    }

    @Override
    public void handler(ConcertSeatReservationRequestedEvent event, Acknowledgment ack) {
        ConcertSeatReservationCache concertSeatReservation = concertCacheService.getConcertSeatReservation(
            event.getConcertId(),
            event.getUserId(),
            event.getScheduleId()
        ).orElseThrow(CommonNotFoundException::new);

        ConcertSeat concertSeat = concertSeatService.findConcert(
            event.getConcertId(),
            event.getUserId(),
            event.getScheduleId(),
            concertSeatReservation.areaId(),
            concertSeatReservation.seatRow(),
            concertSeatReservation.seatColumn()
        ).orElseThrow(CommonNotFoundException::new);

        if (!concertSeatService.updateConcertSeatStatus(
            concertSeat.getId(),
            SeatStatus.AVAILABLE
        )) {
            throw new CommonInternalServerException("seat status update failed");
        }
    }
}
