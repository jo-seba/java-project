package com.concertticketing.userapi.apis.concerts.service;

import static com.concertticketing.userapi.fixture.ConcertFixture.*;
import static com.concertticketing.userapi.fixture.constant.ErrorExceptionConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;
import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.dto.ConcertsDto;
import com.concertticketing.userapi.apis.concerts.exception.ConcertErrorCode;
import com.concertticketing.userapi.apis.concerts.exception.ConcertErrorException;
import com.concertticketing.userapi.apis.concerts.repository.ConcertRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Service] ConcertSearchService 유닛 테스트")
class ConcertSearchServiceTest {
    @InjectMocks
    private ConcertSearchService concertSearchService;

    @Mock
    private ConcertRepository concertRepository;

    @DisplayName("콘서트 단일 조회")
    @Nested
    class GetConcertTest {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("콘서트 조회")
            @Test
            void getConcert() {
                Long id = 1L;
                Concert mockConcert = newConcert(id);
                given(concertRepository.findConcert(id))
                    .willReturn(Optional.of(mockConcert));

                Concert result = concertSearchService.findConcert(id);

                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(id);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("콘서트 존재하지 않음")
            @Test
            void concert_notFound() {
                Long id = 123L;
                given(concertRepository.findConcert(id))
                    .willReturn(Optional.empty());

                assertThatThrownBy(() -> concertSearchService.findConcert(id))
                    .isInstanceOf(ConcertErrorException.class)
                    .extracting(BASE_ERROR_CODE)
                    .isEqualTo(ConcertErrorCode.CONCERT_NOT_FOUND);
            }
        }
    }

    @DisplayName("콘서트 리스트 조회")
    @Nested
    class GetConcertsTest {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("콘서트 10개 조회")
            @Test
            void getConcerts() {
                int size = 10;
                Pageable pageable = PageRequest.of(0, size);
                List<ConcertsDto.ConcertListItem> items = newConcertListItems(10);
                Page<ConcertsDto.ConcertListItem> mockPageConcerts = new PageImpl<>(items, pageable, items.size());

                given(concertRepository.findConcerts(ConcertSort.NEWEST, pageable))
                    .willReturn(mockPageConcerts);

                Page<ConcertsDto.ConcertListItem> result = concertSearchService.findConcerts(ConcertSort.NEWEST,
                    pageable);

                assertThat(result).isNotNull();
                assertThat(result.getContent().size()).isEqualTo(size);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("콘서트 존재하지 않음")
            @Test
            void concert_pageOverflow() {
                Long id = 123L;
                given(concertRepository.findConcert(id))
                    .willReturn(Optional.empty());

                assertThatThrownBy(() -> concertSearchService.findConcert(id))
                    .isInstanceOf(ConcertErrorException.class)
                    .extracting(BASE_ERROR_CODE)
                    .isEqualTo(ConcertErrorCode.CONCERT_NOT_FOUND);
            }
        }
    }
}