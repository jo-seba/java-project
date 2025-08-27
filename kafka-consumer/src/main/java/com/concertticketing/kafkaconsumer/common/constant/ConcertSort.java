package com.concertticketing.kafkaconsumer.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConcertSort {
    POPULAR,
    NEWEST;

    @JsonValue
    public String getDescription() {
        return this.name().toLowerCase();
    }
}

