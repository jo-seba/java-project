package com.concertticketing.commonutils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class TimeUtils {
    public static final ZoneId DEFAULT_ZONE = ZoneId.of("UTC");

    public static long epochSeconds() {
        return Instant.now().getEpochSecond();
    }

    public static long epochSecondsAfter(Duration duration) {
        return Instant.now().plus(duration).getEpochSecond();
    }

    public static long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(DEFAULT_ZONE).toInstant().getEpochSecond();
    }

    public static LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant.atZone(DEFAULT_ZONE).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(long epochSeconds) {
        return Instant.ofEpochSecond(epochSeconds).atZone(DEFAULT_ZONE).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(DEFAULT_ZONE);
    }
}
