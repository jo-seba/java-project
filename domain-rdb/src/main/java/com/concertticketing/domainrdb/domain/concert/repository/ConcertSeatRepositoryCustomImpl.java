package com.concertticketing.domainrdb.domain.concert.repository;

import static com.concertticketing.commonutils.TimeUtils.*;
import static com.concertticketing.domainrdb.domain.concert.domain.QConcertSeat.*;

import java.time.LocalDateTime;

import com.concertticketing.domainrdb.domain.concert.enums.SeatStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConcertSeatRepositoryCustomImpl implements ConcertSeatRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean reserveConcertSeat(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn,
        LocalDateTime holdExpiredAt
    ) {
        return queryFactory
            .update(concertSeat)
            .set(concertSeat.status, SeatStatus.HOLD)
            .set(concertSeat.holdUserId, userId)
            .set(concertSeat.holdExpiredAt, holdExpiredAt)
            .where(
                concertSeat.concertId.eq(concertId)
                    .and(concertSeat.scheduleId.eq(scheduleId))
                    .and(concertSeat.areaId.eq(areaId))
                    .and(concertSeat.seatRow.eq(seatRow))
                    .and(concertSeat.seatColumn.eq(seatColumn))
                    .and(
                        concertSeat.status.eq(SeatStatus.AVAILABLE)
                            .or(
                                concertSeat.status.eq(SeatStatus.HOLD)
                                    .and(concertSeat.holdExpiredAt.loe(now()))
                            )
                    )
            )
            .execute() == 1L;
    }

    @Override
    public boolean ongoingConcertSeat(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn
    ) {
        return queryFactory
            .update(concertSeat)
            .set(concertSeat.status, SeatStatus.ONGOING)
            .where(
                concertSeat.concertId.eq(concertId)
                    .and(concertSeat.scheduleId.eq(scheduleId))
                    .and(concertSeat.areaId.eq(areaId))
                    .and(concertSeat.seatRow.eq(seatRow))
                    .and(concertSeat.seatColumn.eq(seatColumn))
                    .and(concertSeat.status.eq(SeatStatus.HOLD))
                    .and(concertSeat.holdUserId.loe(userId))
                    .and(concertSeat.holdExpiredAt.goe(now()))
            )
            .execute() == 1L;
    }
}