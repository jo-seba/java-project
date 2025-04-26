package com.concertticketing.api.apis.v1.concerts.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "concert_concert")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String thumbnail;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime bookingStartedAt;
    private LocalDateTime bookingEndedAt;
}
