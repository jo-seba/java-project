package com.concertticketing.userapi.apis.concerts.dto;

import java.util.List;

import com.concertticketing.userapi.apis.venues.dto.VenueAreaDto;
import com.concertticketing.userapi.apis.venues.dto.VenueDto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ConcertDetailDto {
    @Schema(description = "콘서트 조회 응답")
    public record ConcertDetailRes(
        @Schema(description = "콘서트 ID", example = "1")
        Long id,
        @Schema(description = "콘서트 이름")
        String title,
        @Schema(description = "콘서트 설명")
        String description,
        @Schema(description = "thumbnail")
        String thumbnail,
        @Schema(description = "콘서트 시작")
        String startedAt,
        @Schema(description = "콘서트 종료")
        String endedAt,
        @Schema(description = "예약 시작")
        String bookingStartedAt,
        @Schema(description = "예약 종료")
        String bookingEndedAt,
        @Schema(description = "공연장 정보")
        VenueDto venue,
        @Schema(description = "공연장 내 예약 구역 정보")
        List<VenueAreaDto> areas,
        @Schema(description = "디테일 이미지들")
        List<String> detailImages,
        @Schema(description = "카테고리들")
        List<String> categories
    ) {
    }
}
