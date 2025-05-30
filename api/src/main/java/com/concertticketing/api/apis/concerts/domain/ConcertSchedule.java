package com.concertticketing.api.apis.concerts.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "concert_schedule")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ConcertSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
