package com.concertticketing.userapi.fixture.common;

import java.util.Random;
import java.util.UUID;

public final class RandomStringData {
    private static final Random RANDOM = new Random();

    public static String randomText(String prefix) {
        return prefix + " " + randomUUID(5);
    }

    public static String randomUrl() {
        return "https://example.com/random/" + randomUUID(8);
    }

    public static String randomUUID(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

}
