package com.concertticketing.userapi.apis.concerts.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.userapi.apis.concerts.dto.ConcertsDto;
import com.concertticketing.userapi.apis.concerts.dto.ConcertsDto.ConcertListRes;
import com.concertticketing.userapi.apis.concerts.facade.ConcertFacade;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "concert", description = "콘서트 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
public class ConcertController {
    private final ConcertFacade concertFacade;

    @Operation(summary = "콘서트 리스트 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertListRes.class))),
        @ApiResponse(responseCode = "404", description = "page overflow")
    })
    @GetMapping
    public ResponseEntity<ConcertListRes> getConcerts(
        @ModelAttribute @Validated ConcertsDto.ConcertListQuery query
    ) {
        return ResponseEntity.ok(concertFacade.getConcerts(query));
    }

    @Operation(summary = "콘서트 디테일 조회")
    @Parameter(name = "id", required = true, in = PATH)
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertDetailRes.class))),
        @ApiResponse(responseCode = "404", description = "not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConcertDetailRes> getConcert(
        @AuthenticationPrincipal Long userId,
        @PathVariable @Min(1) Long id
    ) {
        return ResponseEntity.ok(concertFacade.getConcert(id, userId));
    }
}
