package com.concertticketing.userapi.apis.concerts.domain;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_ticketing_config")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertTicketingConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer capacity;
}
