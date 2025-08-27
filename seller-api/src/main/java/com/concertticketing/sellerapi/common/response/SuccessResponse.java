package com.concertticketing.sellerapi.common.response;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessResponse<T> {
    private String code;
    private T data;

    private SuccessResponse(T data) {
        this(HttpStatus.OK, data);
    }

    private SuccessResponse(HttpStatus statusCode, T data) {
        this.code = String.valueOf(statusCode.value());
        this.data = data;
    }

    public static <V> SuccessResponse<Map<String, V>> from(String key, V data) {
        return new SuccessResponse<>(Map.of(key, data));
    }

    public static <T> SuccessResponse<T> from(T data) {
        return new SuccessResponse<>(data);
    }

    public static SuccessResponse<?> noContent() {
        return new SuccessResponse<>(HttpStatus.NO_CONTENT, Map.of());
    }

    public static SuccessResponse<?> created() {
        return new SuccessResponse<>(HttpStatus.CREATED, Map.of());
    }
}
