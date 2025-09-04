package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.ConcertSeatReservationDLQEvent;
import com.concertticketing.commonavro.ConcertSeatReservationRequestedEvent;
import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.commonerror.exception.common.CommonInternalServerException;
import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainrdb.domain.concert.domain.ConcertSeat;
import com.concertticketing.domainrdb.domain.concert.enums.SeatStatus;
import com.concertticketing.domainredis.common.constant.ConcertPaymentEventStatus;
import com.concertticketing.domainredis.domain.concert.domain.ConcertSeatReservationCache;
import com.concertticketing.kafkaconsumer.common.handler.manual.ConsumerRetryAndDLQManualHandler;
import com.concertticketing.kafkaconsumer.common.kafka.KafkaProducer;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertPaymentService;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertSeatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertSeatReservationRequestedHandler
    implements ConsumerRetryAndDLQManualHandler<ConcertSeatReservationRequestedEvent> {
    private final KafkaProducer kafkaProducer;

    private final ConcertCacheService concertCacheService;

    private final ConcertSeatService concertSeatService;

    private final ConcertPaymentService concertPaymentService;

    @Override
    @KafkaListener(
        topics = {CONCERT_SEAT_RESERVATION_REQUESTED},
        groupId = CONCERT_SEAT_RESERVATION,
        containerFactory = SINGLE_MANUAL_ACK_CONSUMER_FACTORY
    )
    public void consume(ConcertSeatReservationRequestedEvent event, Acknowledgment ack) {
        try {
            handler(event, ack);
        } catch (CommonConflictException e) {
            log.info("duplicate event: {}", event.getEventId());
        } catch (Exception e) {
            if (event.getMaxRetryCount() > event.getRetryCount()) {
                handleRetry(event, ack, e);
            } else {
                handleDLQ(event, ack, e);
            }
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

        if (!concertSeatReservation.canReservationEventStart()) {
            if (concertSeatReservation.isWaiting()) {
                throw new CommonBadRequestException();
            } else {
                throw new CommonConflictException();
            }
        }

        ConcertSeat concertSeat = concertSeatService.findConcert(
            event.getConcertId(),
            event.getUserId(),
            event.getScheduleId(),
            concertSeatReservation.areaId(),
            concertSeatReservation.seatRow(),
            concertSeatReservation.seatColumn()
        ).orElseThrow(CommonNotFoundException::new);
        if (!concertSeat.getHoldUserId().equals(event.getUserId())) {
            throw new CommonBadRequestException();
        }
        if (concertSeat.isOngoing()) {
            throw new CommonConflictException();
        }

        if (!concertCacheService.setConcertPaymentEventStatus(
            event.getEventId(),
            ConcertPaymentEventStatus.ONGOING
        )) {
            throw new CommonConflictException();
        }

        if (!concertPaymentService.processPayment()) {
            throw new CommonInternalServerException("Random Error");
        }

        if (!concertSeatService.updateConcertSeatStatus(
            concertSeat.getId(),
            SeatStatus.SOLD
        )) {
            throw new CommonInternalServerException("seat status update failed");
        }
    }

    @Override
    public void handleRetry(ConcertSeatReservationRequestedEvent event, Acknowledgment ack, Exception e) {
        kafkaProducer.send(
            CONCERT_SEAT_RESERVATION_REQUESTED,
            new ConcertSeatReservationRequestedEvent(
                event.getEventId(),
                event.getConcertId(),
                event.getUserId(),
                event.getScheduleId(),
                event.getRetryCount() + 1,
                event.getMaxRetryCount()
            )
        );
    }

    @Override
    public void handleDLQ(ConcertSeatReservationRequestedEvent event, Acknowledgment ack, Exception e) {
        log.error(e.getMessage());
        kafkaProducer.send(
            CONCERT_SEAT_RESERVATION_DLQ,
            new ConcertSeatReservationDLQEvent(
                event.getEventId(),
                event.getConcertId(),
                event.getUserId(),
                event.getScheduleId()
            )
        );
    }
}
