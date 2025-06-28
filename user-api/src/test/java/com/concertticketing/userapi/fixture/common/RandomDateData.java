package com.concertticketing.userapi.fixture.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public final class RandomDateData {
    private static final Random RANDOM = new Random();

    public static LocalDateTime randomPastDateTimeMaxDays(int maxDays) {
        LocalDateTime now = LocalDateTime.now();
        return randomDateTimeBetween(
            now.minusDays(maxDays),
            now.minusSeconds(1)
        );
    }

    public static LocalDateTime randomFutureDateTimeMaxDays(int maxDays) {
        LocalDateTime now = LocalDateTime.now();
        return randomDateTimeBetween(
            now.plusSeconds(1),
            now.plusDays(maxDays)
        );
    }

    public static LocalDateTime randomDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        long startEpoch = start.toEpochSecond(ZoneOffset.UTC);
        long endEpoch = end.toEpochSecond(ZoneOffset.UTC);

        long randomEpoch = startEpoch + RANDOM.nextLong(endEpoch - startEpoch - 1) + 1;

        return LocalDateTime.ofEpochSecond(randomEpoch, 0, ZoneOffset.UTC);
    }
}
