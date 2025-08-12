package com.concertticketing.userapi.apis.concerts.dto;

import java.util.List;

import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertListItemDBDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public class ConcertListDto {
    public record ConcertListQuery(
        @Schema(description = "정렬 기준", example = "newest", defaultValue = "newest", implementation = ConcertSort.class)
        ConcertSort sort,
        @Schema(description = "page >= 1", example = "1")
        @Min(1)
        int page
    ) {
        public ConcertSort getConcertSort() {
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
        List<ConcertListItemDBDto> concerts
    ) {
    }
}
