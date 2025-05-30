package com.concertticketing.api.apis.concerts.api;

import com.concertticketing.api.apis.concerts.dto.ConcertDetailDto;
import com.concertticketing.api.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto.ConcertListQuery;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto.ConcertListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "concert", description = "콘서트 API")
public interface ConcertApi {
    @Operation(summary = "콘서트 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertListRes.class))),
            @ApiResponse(responseCode = "404", description = "page overflow")
    })
    public ResponseEntity<ConcertListRes> getConcerts(@ParameterObject @ModelAttribute @Validated ConcertListQuery query);

    @Operation(summary = "콘서트 디테일 조회")
    @Parameter(name = "id", required = true, in = PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ConcertDetailRes.class))),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    public ResponseEntity<ConcertDetailRes> getConcert(@PathVariable @Min(1) Long id);
}
