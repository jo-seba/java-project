package com.concertticketing.kafkaconsumer.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum SeatStatus {
    AVAILABLE(0),
    HOLD(1),
    ONGOING(2),
    SOLD(3);

    private final int dbValue;

    private static final Map<Integer, SeatStatus> CACHE_MAP = new HashMap<>();

    static {
        for (SeatStatus role : SeatStatus.values()) {
            CACHE_MAP.put(role.dbValue, role);
        }
    }

    SeatStatus(int dbValue) {
        this.dbValue = dbValue;
    }

    public int getDbValue() {
        return dbValue;
    }

    public static SeatStatus from(int dbValue) {
        SeatStatus result = CACHE_MAP.get(dbValue);
        if (result == null) {
            throw new IllegalArgumentException("Invalid SeatStatus: " + dbValue);
        }
        return result;
    }
}
