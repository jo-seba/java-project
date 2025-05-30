package com.concertticketing.api.apis.concerts.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "concert_detail_image")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ConcertDetailImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
}
