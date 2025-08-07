package com.concertticketing.userapi.common.mapstruct;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

@Component
public class TimeMapStructHelper {
    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Seoul");

    public long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(DEFAULT_ZONE).toInstant().getEpochSecond();
    }
}
