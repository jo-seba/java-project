package com.concertticketing.kafkaconsumer.domain.concert.domain;

import java.time.LocalDateTime;

import com.concertticketing.kafkaconsumer.common.constant.SeatStatus;
import com.concertticketing.kafkaconsumer.common.converter.jpa.SeatStatusConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_seat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertId;
    private Long scheduleId;
    private Long areaId;
    private int seatRow;
    private int seatColumn;
    @Convert(converter = SeatStatusConverter.class)
    private SeatStatus status;
    private Long holdUserId;
    private LocalDateTime holdExpiredAt;

    public boolean isOngoing() {
        return status == SeatStatus.ONGOING;
    }
}

