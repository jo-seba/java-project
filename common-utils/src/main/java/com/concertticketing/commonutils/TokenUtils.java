package com.concertticketing.commonutils;

import java.security.SecureRandom;
import java.util.Base64;

public final class TokenUtils {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder();

    public static String opaqueTokenGenerate() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return encoder.withoutPadding().encodeToString(randomBytes);
    }
}
