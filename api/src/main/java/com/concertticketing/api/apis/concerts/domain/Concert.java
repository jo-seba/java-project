package com.concertticketing.api.apis.concerts.domain;

import com.concertticketing.api.apis.venues.domain.Venue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concert_concert")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"venue", "detailImages"})
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long VenueLayoutId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany
    @JoinColumn(name = "concert_id")
    private List<ConcertDetailImage> detailImages = new ArrayList<>();
}
