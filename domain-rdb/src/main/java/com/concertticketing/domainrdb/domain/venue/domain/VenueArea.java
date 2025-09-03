package com.concertticketing.domainrdb.domain.venue.domain;

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

    private Long layoutId;

    private String name;
    private BigDecimal price;

    public VenueArea(
        Long layoutId
    ) {
        this.layoutId = layoutId;
    }
}

