package com.concertticketing.sellerapi.apis.concerts.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.concertticketing.sellerapi.apis.concerts.controller.fixture.AddConcertBodyFixture;
import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto.AddConcertRes;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.sellerapi.common.annotation.IntegrationTest;
import com.concertticketing.sellerapi.common.client.TestClient;
import com.concertticketing.sellerapi.common.response.SuccessResponse;

@IntegrationTest
@DisplayName("[Controller] ConcertController - /concerts")
class ConcertControllerTest {

    @DisplayName("[POST] /api/v1/concerts - 콘서트 등록")
    @Nested
    class AddConcert {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("콘서트 등록 성공")
            @Test
            void addConcert(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder().build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getData().id()).isGreaterThan(0L);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("길이 제한 초과")
            @ParameterizedTest
            @CsvSource({
                // title, description, thumbnail
                "This title is way too long and exceedssss, description, thumbnail",
                "title, This description is way too long and exceeds the maximum length of two hundred and fifty-five characters which is not allowed in the system and should trigger a validation error when attempting to create a new concert with this description descriptionaaaaa, thumbnail",
                "title, description, https://example.com/this-is-a-very-long-thumbnail-url-that-exceeds-the-maximum-length-of-two-hundred-and-fifty-five-characters-which-is-not-allowed-in-the-system-and-should-trigger-a-validation-error-when-attempting-to-create-a-new-concert-with-this-th.png"
            })
            void exceedMaxLength(
                String title,
                String description,
                String thumbnail,
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .title(title)
                        .description(description)
                        .thumbnail(thumbnail)
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("매니저 아닌 판매자 생성 예외")
            @Test
            void underManagerRoleToken(
                @Autowired TestClient client
            ) {
                Integer shopperId = 3;
                client.setDefaultAuthorization(shopperId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder().build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
            }

            @DisplayName("콘서트 시작 날짜 > 종료 날짜")
            @Test
            void startAfterEnd(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .startedAt(AddConcertBodyFixture.getENDED_AT().plusDays(1))
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("예약 시작 날짜 > 예약 종료 날짜")
            @Test
            void bookingStartAfterBookingEnd(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .bookingStartedAt(AddConcertBodyFixture.getBOOKING_ENDED_AT().plusDays(1))
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("예약 시작 날짜 > 콘서트 시작 날짜")
            @Test
            void bookingStartAfterConcertStart(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .bookingStartedAt(AddConcertBodyFixture.getSTARTED_AT().plusDays(1))
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("카테고리 ID 길이 초과")
            @Test
            void categoryIdsSizeExceed(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .categoryIds(List.of(1, 2, 3, 4, 5, 6))
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("카테고리 ID 중복")
            @Test
            void categoryIdsDuplicate(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .categoryIds(List.of(1, 2, 2))
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("이미지 길이 초과")
            @Test
            void detailImageUrlsSizeExceed(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .detailImages(
                            List.of(
                                "https://example.com/image1.png",
                                "https://example.com/image2.png",
                                "https://example.com/image3.png",
                                "https://example.com/image4.png",
                                "https://example.com/image5.png",
                                "https://example.com/image6.png",
                                "https://example.com/image7.png",
                                "https://example.com/image8.png"
                            )
                        )
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @DisplayName("이미지 중복")
            @Test
            void detailImageUrlsDuplicate(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<AddConcertRes>> response = client.post(
                    "/api/v1/concerts",
                    AddConcertBodyFixture.builder()
                        .detailImages(
                            List.of(
                                "https://example.com/image.png",
                                "https://example.com/image.png"
                            )
                        )
                        .build(),
                    AddConcertRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DisplayName("[GET] /api/v1/concerts/{id} - 단일 콘서트 조회")
    @Nested
    class GetConcert {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("단일 콘서트 조회 성공")
            @Test
            void getConcert(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                Long concertId = 1L;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<ConcertDetailRes>> response = client.get(
                    "/api/v1/concerts/" + concertId,
                    ConcertDetailRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getData()).isNotNull();
                assertThat(response.getBody().getData().id()).isEqualTo(concertId);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("존재하지 않는 콘서트")
            @Test
            void notFoundConcert(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                Long notFoundConcertId = 999L;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<ConcertDetailRes>> response = client.get(
                    "/api/v1/concerts/" + notFoundConcertId,
                    ConcertDetailRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            }

            @DisplayName("다른 판매자 콘서트 조회")
            @Test
            void getOtherSellerConcert(
                @Autowired TestClient client
            ) {
                Integer sellerId = 1;
                Long otherSellerConcertId = 2L;
                client.setDefaultAuthorization(sellerId);

                ResponseEntity<SuccessResponse<ConcertDetailRes>> response = client.get(
                    "/api/v1/concerts/" + otherSellerConcertId,
                    ConcertDetailRes.class
                );

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            }
        }
    }
}