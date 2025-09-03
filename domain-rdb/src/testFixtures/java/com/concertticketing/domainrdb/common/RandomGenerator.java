package com.concertticketing.domainrdb.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;

public final class RandomGenerator {
    private static final Random RANDOM = new Random();

    private RandomGenerator() {
    }

    // number
    public static BigDecimal randomLatitude() {
        double val = -90 + 180 * RANDOM.nextDouble();
        return BigDecimal.valueOf(val).setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal randomLongitude() {
        double val = -180 + 360 * RANDOM.nextDouble();
        return BigDecimal.valueOf(val).setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    public static Integer randomPositiveNum() {
        return RANDOM.nextInt(1000) + 1;
    }

    public static Long randomPositiveNumLong() {
        return RANDOM.nextLong(1000) + 1;
    }

    public static Integer randomPositiveNum(int max) {
        return RANDOM.nextInt(max) + 1;
    }

    public static Integer randomPositiveNum(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static Long randomPositiveNum(long min, long max) {
        return RANDOM.nextLong(max - min + 1) + min;
    }

    // Date
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

    // String
    public static String randomText() {
        return randomUUID(10);
    }

    public static String randomText(String prefix) {
        return prefix + " " + randomUUID(5);
    }

    public static String randomUrl() {
        return "https://example.com/random/" + randomUUID(8);
    }

    public static String randomUUID(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

    public static String randomPhoneNumber() {
        return String.format("010%04d%04d", RANDOM.nextInt(10000), RANDOM.nextInt(10000));
    }
}
