package com.concertticketing.userapi.apis.concerts.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.userapi.apis.concerts.dto.ConcertsDto.ConcertListRes;
import com.concertticketing.userapi.common.annotation.IntegrationTest;
import com.concertticketing.userapi.common.client.TestClient;

@IntegrationTest
@DisplayName("[Controller] ConcertController - /concerts")
class ConcertControllerTest {

    @DisplayName("[GET] /api/v1/concerts - 콘서트 목록 조회")
    @Nested
    class GetConcerts {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("콘서트 목록을 조회")
            @Test
            void getConcerts(
                @Autowired TestClient client
            ) {
                String uri = UriComponentsBuilder.fromPath("/api/v1/concerts")
                    .queryParam("sort", "NEWEST")
                    .queryParam("page", 1)
                    .toUriString();

                ResponseEntity<ConcertListRes> result = client.get(uri, ConcertListRes.class);

                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(result.getBody()).isNotNull();
                assertThat(result.getBody().currentPage()).isEqualTo(1);
                assertThat(result.getBody().concerts()).isNotNull();
                assertThat(result.getBody().concerts().size()).isGreaterThan(0);
            }

            @DisplayName("빈 콘서트 목록 조회")
            @Test
            void getEmptyConcerts(
                @Autowired TestClient client
            ) {
                String uri = UriComponentsBuilder.fromPath("/api/v1/concerts")
                    .queryParam("sort", "NEWEST")
                    .queryParam("page", 1000)
                    .toUriString();

                ResponseEntity<ConcertListRes> result = client.get(uri, ConcertListRes.class);

                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(result.getBody()).isNotNull();
                assertThat(result.getBody().currentPage()).isEqualTo(1000);
                assertThat(result.getBody().concerts()).isNotNull();
                assertThat(result.getBody().concerts().size()).isEqualTo(0);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("page가 1 미만")
            @Test
            void invalidPage(
                @Autowired TestClient client
            ) {
                String uri = UriComponentsBuilder.fromPath("/api/v1/concerts")
                    .queryParam("sort", "NEWEST")
                    .queryParam("page", 0)
                    .toUriString();

                ResponseEntity<ConcertListRes> result = client.get(uri, ConcertListRes.class);

                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("알 수 없는 정렬 기준")
            @Test
            void invalidSort(
                @Autowired TestClient client
            ) {
                String uri = UriComponentsBuilder.fromPath("/api/v1/concerts")
                    .queryParam("sort", "UNKNOWN")
                    .queryParam("page", 1)
                    .toUriString();

                ResponseEntity<ConcertListRes> result = client.get(uri, ConcertListRes.class);

                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DisplayName("[GET] /api/v1/concerts/{id} - 단일 콘서트 조회")
    @Nested
    class GetConcert {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("콘서트를 조회")
            @Test
            void getConcert(
                @Autowired TestClient client
            ) {
                Long id = 20L;

                ResponseEntity<ConcertDetailRes> result = client.get(
                    "/api/v1/concerts/" + id,
                    ConcertDetailRes.class
                );

                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(result.getBody()).isNotNull();
                assertThat(result.getBody().id()).isEqualTo(id);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("존재하지 않는 콘서트")
            @Test
            void notFound(
                @Autowired TestClient client
            ) {
                Long id = 100000L;

                ResponseEntity<ConcertDetailRes> result = client.get(
                    "/api/v1/concerts/" + id,
                    ConcertDetailRes.class
                );

                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            }

            @DisplayName("id가 1 미만")
            @Test
            void invalidId(
                @Autowired TestClient client
            ) {
                Long id = 0L;

                ResponseEntity<ConcertDetailRes> result = client.get(
                    "/api/v1/concerts/" + id,
                    ConcertDetailRes.class
                );

                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }
}