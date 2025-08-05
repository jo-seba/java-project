package com.concertticketing.userapi.apis.concerts.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Immutable;

import com.concertticketing.userapi.apis.venues.domain.Venue;
import com.concertticketing.userapi.apis.venues.domain.VenueLayout;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "concert")
@Immutable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer duration;
    private Long viewCount;
    private String description;
    private String thumbnail;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime bookingStartedAt;
    private LocalDateTime bookingEndedAt;

    private LocalDateTime createdAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_layout_id")
    private VenueLayout venueLayout;

    @ToString.Exclude
    @OneToMany(mappedBy = "concert")
    private List<ConcertConcertCategory> concertCategories = new ArrayList<>();

    @ToString.Exclude
    @OneToMany
    @JoinColumn(name = "concert_id")
    private List<ConcertDetailImage> detailImages = new ArrayList<>();
}
