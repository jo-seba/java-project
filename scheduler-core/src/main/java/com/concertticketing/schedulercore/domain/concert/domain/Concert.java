package com.concertticketing.schedulercore.domain.concert.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "concert")
@Immutable
@SQLRestriction("is_deleted IS FALSE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingStartedAt;
    private LocalDateTime bookingEndedAt;
}
