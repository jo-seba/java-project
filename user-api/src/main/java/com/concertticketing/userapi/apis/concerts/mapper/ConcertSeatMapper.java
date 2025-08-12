package com.concertticketing.userapi.apis.concerts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.concertticketing.domainredis.common.constant.ConcertSeatReservationStatus;
import com.concertticketing.domainredis.domain.concert.domain.ConcertSeatReservationCache;
import com.concertticketing.userapi.apis.concerts.dto.PerformConcertSeatPaymentDto.PerformConcertSeatPaymentReq;
import com.concertticketing.userapi.common.mapstruct.MapStructBaseConfig;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface ConcertSeatMapper {
    @Mapping(target = "billingKey", source = "body.billingKey")
    @Mapping(target = "scheduleId", source = "body.scheduleId")
    @Mapping(target = "areaId", source = "body.areaId")
    @Mapping(target = "seatRow", source = "body.seatRow")
    @Mapping(target = "seatColumn", source = "body.seatColumn")
    ConcertSeatReservationCache toConcertSeatReservationCache(
        String eventId,
        Long concertId,
        Long userId,
        PerformConcertSeatPaymentReq body,
        ConcertSeatReservationStatus status
    );
}
