package com.concertticketing.userapi.fixture.common;

import java.math.BigDecimal;
import java.util.Random;

public final class RandomNumberData {
    private static final Random RANDOM = new Random();

    public static BigDecimal randomLatitude() {
        double val = -90 + 180 * RANDOM.nextDouble();
        return BigDecimal.valueOf(val).setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal randomLongitude() {
        double val = -180 + 360 * RANDOM.nextDouble();
        return BigDecimal.valueOf(val).setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    public static Integer randomPositiveNum() {
        return RANDOM.nextInt(100) + 1;
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
}
