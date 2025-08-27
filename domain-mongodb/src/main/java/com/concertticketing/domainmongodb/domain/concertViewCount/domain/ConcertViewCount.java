package com.concertticketing.domainmongodb.domain.concertViewCount.domain;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "concert_view_counts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ConcertViewCount {
    @Id
    private ObjectId id;
    private Long concertId;
    private Long userId;
    private LocalDateTime viewedAt;

    public ConcertViewCount(Long concertId, Long userId, LocalDateTime viewedAt) {
        this.concertId = concertId;
        this.userId = userId;
        this.viewedAt = viewedAt;
    }
}
