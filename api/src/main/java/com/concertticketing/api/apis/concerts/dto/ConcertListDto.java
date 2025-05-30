package com.concertticketing.api.apis.concerts.dto;

import com.concertticketing.api.apis.concerts.constant.ConcertSort;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertListDto {
    public record ConcertListQuery(
            @Schema(description = "정렬 기준", example = "newest", defaultValue = "newest", implementation = ConcertSort.class)
            ConcertSort sort,
            @Schema(description = "page >= 1", example = "1")
            @Min(1)
            int page
    ) {
        public ConcertSort getConcertSortEnum() {
            return sort != null ? sort : ConcertSort.NEWEST;
        }

        public int getPageablePage() {
            return page - 1;
        }
    }

    public record ConcertListRes(
            @Schema(description = "요청한 페이지")
            int currentPage,
            @Schema(description = "전체 페이지")
            int totalPage,
            @Schema(description = "콘서트 리스트")
            List<ConcertListItem> concerts
    ) {}

    public record ConcertListItem(
            @Schema(description = "id")
            Long id,
            @Schema(description = "콘서트 이름")
            String title,
            @Schema(description = "공연장 이름")
            String venueName,
            @Schema(description = "섬네일")
            String thumbnail,
            @Schema(description = "콘서트 시작")
            LocalDateTime startedAt,
            @Schema(description = "콘서트 종료")
            LocalDateTime endedAt
    ) {
        @QueryProjection
        public ConcertListItem {}
    }
}
