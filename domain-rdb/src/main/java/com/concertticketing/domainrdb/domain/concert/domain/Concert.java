package com.concertticketing.domainrdb.domain.concert.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.concertticketing.domainrdb.common.entity.DateAuditable;
import com.concertticketing.domainrdb.domain.company.domain.Company;
import com.concertticketing.domainrdb.domain.venue.domain.Venue;
import com.concertticketing.domainrdb.domain.venue.domain.VenueLayout;

import jakarta.persistence.CascadeType;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "concert")
@SQLRestriction("is_deleted IS FALSE")
@SQLDelete(sql = "UPDATE concert SET is_deleted = TRUE and deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Concert extends DateAuditable {
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

    @Getter(AccessLevel.NONE)
    private Boolean isDeleted;

    @Getter(AccessLevel.NONE)
    private LocalDateTime deletedAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_layout_id")
    private VenueLayout venueLayout;

    @ToString.Exclude
    @OneToMany(mappedBy = "concert", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ConcertConcertCategory> concertCategories = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "concert_id")
    private List<ConcertDetailImage> detailImages = new ArrayList<>();

    public Concert(
        String title,
        Integer duration,
        String description,
        String thumbnail,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime bookingStartedAt,
        LocalDateTime bookingEndedAt,
        Company company,
        Venue venue,
        VenueLayout venueLayout
    ) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.duration = Objects.requireNonNull(duration, "duration cannot be null");
        this.viewCount = 0L;
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.thumbnail = Objects.requireNonNull(thumbnail, "thumbnail cannot be null");
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt cannot be null");
        this.endedAt = Objects.requireNonNull(endedAt, "endedAt cannot be null");
        this.bookingStartedAt = Objects.requireNonNull(bookingStartedAt, "bookingStartedAt cannot be null");
        this.bookingEndedAt = Objects.requireNonNull(bookingEndedAt, "bookingEndedAt cannot be null");

        this.isDeleted = false;

        validate(startedAt, endedAt, bookingStartedAt, bookingEndedAt);

        this.company = Objects.requireNonNull(company, "company cannot be null");
        this.venue = Objects.requireNonNull(venue, "venue cannot be null");
        this.venueLayout = Objects.requireNonNull(venueLayout, "venueLayout cannot be null");
    }

    public void update(
        String title,
        Integer duration,
        String description,
        String thumbnail,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime bookingStartedAt,
        LocalDateTime bookingEndedAt,
        Company company,
        Venue venue,
        VenueLayout venueLayout
    ) {
        if (LocalDateTime.now().isAfter(bookingStartedAt)) {
            throw new IllegalArgumentException("now cannot be after bookingEndedAt.");
        }

        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.duration = Objects.requireNonNull(duration, "duration cannot be null");
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.thumbnail = Objects.requireNonNull(thumbnail, "thumbnail cannot be null");
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt cannot be null");
        this.endedAt = Objects.requireNonNull(endedAt, "endedAt cannot be null");
        this.bookingStartedAt = Objects.requireNonNull(bookingStartedAt, "bookingStartedAt cannot be null");
        this.bookingEndedAt = Objects.requireNonNull(bookingEndedAt, "bookingEndedAt cannot be null");

        validate(startedAt, endedAt, bookingStartedAt, bookingEndedAt);

        this.company = Objects.requireNonNull(company, "company cannot be null");
        this.venue = Objects.requireNonNull(venue, "venue cannot be null");
        this.venueLayout = Objects.requireNonNull(venueLayout, "venueLayout cannot be null");
    }

    private void validate(
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime bookingStartedAt,
        LocalDateTime bookingEndedAt
    ) {
        if (!startedAt.isBefore(endedAt)) {
            throw new IllegalArgumentException("startedAt must be before endedAt.");
        }
        if (!bookingStartedAt.isBefore(bookingEndedAt)) {
            throw new IllegalArgumentException("bookingEndedAt must be before bookingStartedAt.");
        }
        if (!bookingEndedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("bookingEndedAt must be before startedAt.");
        }
    }
}
