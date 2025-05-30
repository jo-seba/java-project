package com.concertticketing.api.apis.venues.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "venue_area")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class VenueArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // venue_layout 외래키
    private Long layoutId;

    private String name;
    private BigDecimal price;
}
