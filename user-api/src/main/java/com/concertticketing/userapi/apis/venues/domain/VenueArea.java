package com.concertticketing.userapi.apis.venues.domain;

import java.math.BigDecimal;

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
