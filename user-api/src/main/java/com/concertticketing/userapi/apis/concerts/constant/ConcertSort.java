package com.concertticketing.userapi.apis.concerts.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ConcertSort {
    POPULAR,
    NEWEST;

    @JsonValue
    public String getDescription() {
        return this.name().toLowerCase();
    }
}
