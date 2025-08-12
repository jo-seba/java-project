package com.concertticketing.kafkaconsumer.common.mapstruct;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.concertticketing.commonutils.TimeUtils;

@Component
public class TimeMapStructHelper {
    public LocalDateTime localDateTimeToLong(long localDateTime) {
        return TimeUtils.toLocalDateTime(localDateTime);
    }
}

