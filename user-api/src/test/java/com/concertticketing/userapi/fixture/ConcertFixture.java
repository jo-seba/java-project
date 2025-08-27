package com.concertticketing.userapi.fixture;

import static com.concertticketing.userapi.fixture.common.RandomDateData.*;
import static com.concertticketing.userapi.fixture.common.RandomNumberData.*;
import static com.concertticketing.userapi.fixture.common.RandomStringData.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.dto.ConcertsDto;

public final class ConcertFixture {
    private static final int DEFAULT_DAY_RANGE = 30;

    public static Concert newConcert(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startedAt = newStartedAt(now);
        LocalDateTime endedAt = newEndedAt(startedAt);
        LocalDateTime bookingStartedAt = newBookingStartedAt(startedAt);

        return new Concert(
            id,
            1L,
            randomText("title"),
            randomPositiveNum(),
            randomPositiveNum(100L, 1000L),
            randomText("description"),
            randomText("thumbnail"),
            startedAt,
            endedAt,
            newBookingStartedAt(startedAt),
            newBookingEndedAt(endedAt, bookingStartedAt),
            now,
            VenueFixture.newVenue(randomPositiveNum(3)),
            ConcertDetailImageFixture.newConcertDetailImages(randomPositiveNum(5))
        );
    }

    public static List<ConcertsDto.ConcertListItem> newConcertListItems(int length) {
        List<ConcertsDto.ConcertListItem> results = new ArrayList<>();
        for (int i = 1; i <= length; i++) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startedAt = newStartedAt(now);
            results.add(newConcertListItem((long)i));
        }

        return results;
    }

    /**
     * 현재 날짜 +- DEFAULT_DAY_RANGE 기준으로 랜덤하게 값 추출
     * @param now 현재 시각
     * @return
     */
    private static LocalDateTime newStartedAt(LocalDateTime now) {
        return randomDateTimeBetween(now.minusDays(DEFAULT_DAY_RANGE), now.plusDays(DEFAULT_DAY_RANGE));
    }

    /**
     * 종료 시각은 2~14일 후
     * @param startedAt 설정된 시작 시각
     */
    private static LocalDateTime newEndedAt(LocalDateTime startedAt) {
        return randomDateTimeBetween(startedAt.plusDays(2), startedAt.plusDays(14));
    }

    /**
     * 예약 시작 시간은 3~5주 전
     * @param startedAt 설정된 시작 시각
     */
    private static LocalDateTime newBookingStartedAt(LocalDateTime startedAt) {
        return randomDateTimeBetween(startedAt.minusWeeks(5), startedAt.minusWeeks(3));
    }

    /**
     * 예약 종료일은 예약 시작 + 1주(최대 시작 2주 전) ~ 종료 시각 - 2시간
     * @param endedAt 종료 시각
     * @param bookingStartedAt newBookingStartedAt에서 반환된 값
     */
    private static LocalDateTime newBookingEndedAt(LocalDateTime endedAt, LocalDateTime bookingStartedAt) {
        return randomDateTimeBetween(bookingStartedAt.plusWeeks(1), endedAt.minusHours(2));
    }

    private static ConcertsDto.ConcertListItem newConcertListItem(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startedAt = newStartedAt(now);
        return new ConcertsDto.ConcertListItem(
            id,
            randomText("title"),
            randomText("venue name"),
            randomUrl(),
            startedAt,
            newEndedAt(startedAt)
        );
    }
}
