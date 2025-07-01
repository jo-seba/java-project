package com.concertticketing.sellerapi.apis.concerts.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class AddConcertDto {
    public record AddConcertBody(
        @Min(1)
        int venueId,
        @Min(1)
        long venueLayoutId,
        @NotBlank
        @Size(max = 40)
        String title,
        @Schema(description = "러닝타임")
        @Min(1)
        @Max(32000)
        int duration,
        @NotBlank
        @Size(max = 255)
        String description,
        @NotBlank
        @Size(max = 255)
        String thumbnail,
        @NotNull
        @Future
        LocalDateTime startedAt,
        @NotNull
        @Future
        LocalDateTime endedAt,
        @NotNull
        @Future
        LocalDateTime bookingStartedAt,
        @NotNull
        @Future
        LocalDateTime bookingEndedAt,
        @NotNull
        @UniqueElements
        @Size(max = 5)
        List<@NotNull @Min(1) Integer> categoryIds,
        @NotNull
        @UniqueElements
        @Size(max = 7)
        List<@NotBlank @Size(max = 255) String> detailImageUrls
    ) {
        @AssertTrue
        public boolean isStartBeforeEnd() {
            if (startedAt() == null || endedAt() == null) {
                return false;
            }
            return startedAt().isBefore(endedAt());
        }

        @AssertTrue
        public boolean isBookingStartBeforeBookingEnd() {
            if (bookingStartedAt() == null || bookingEndedAt() == null) {
                return false;
            }
            return bookingStartedAt().isBefore(bookingEndedAt());
        }

        @AssertTrue
        public boolean isBookingEndBeforeStart() {
            if (bookingEndedAt() == null || startedAt() == null) {
                return false;
            }
            return bookingEndedAt().isBefore(startedAt());
        }
    }

    public record AddConcertRes(Long id) {
    }
}
