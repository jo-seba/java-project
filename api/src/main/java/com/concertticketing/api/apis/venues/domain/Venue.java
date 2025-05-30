package com.concertticketing.api.apis.venues.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "venue_venue")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer capacity;
    private String roadAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
