package com.concertticketing.userapi.apis.concerts.service;

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

import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainrdb.domain.ConcertFixture;
import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;
import com.concertticketing.domainrdb.domain.concert.enums.ConcertSort;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Service-UnitTest] ConcertService")
class ConcertServiceTest {
    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;

    private final int DEFAULT_PAGE_SIZE = 10;

    @DisplayName("findConcert")
    @Nested
    class FindConcert {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("단일 콘서트 조회")
            @Test
            void findConcert() {
                Long id = 1L;
                Concert concert = ConcertFixture
                    .concertBuilder()
                    .id(id)
                    .build();
                given(concertRepository.findConcert(id))
                    .willReturn(Optional.of(concert));

                Concert result = concertService.findConcert(id);

                assertThat(result).isEqualTo(concert);
                assertThat(result.getId()).isEqualTo(id);
            }
        }

        @DisplayName("실패")
        @Nested
        class Failure {
            @DisplayName("존재하지 않는 콘서트")
            @Test
            void notFound() {
                Long id = 1L;
                given(concertRepository.findConcert(id))
                    .willReturn(Optional.empty());

                assertThatThrownBy(() -> concertService.findConcert(id))
                    .isInstanceOf(CommonNotFoundException.class);
            }
        }
    }

    @DisplayName("findConcerts")
    @Nested
    class FindConcerts {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("콘서트 목록 조회")
            @Test
            void findConcerts() {
                ConcertSort sort = ConcertSort.NEWEST;
                Pageable pageable = PageRequest.of(1, DEFAULT_PAGE_SIZE);
                List<ConcertListDto> items = List.of(
                    ConcertFixture.concertListDtoBuilder()
                        .id(1L)
                        .build(),
                    ConcertFixture.concertListDtoBuilder()
                        .id(2L)
                        .build()
                );
                Page<ConcertListDto> concerts = new PageImpl<>(
                    items,
                    pageable,
                    items.size()
                );
                given(concertRepository.findConcerts(sort, pageable))
                    .willReturn(concerts);

                Page<ConcertListDto> result = concertService.findConcerts(sort, pageable);

                assertThat(result.getSize()).isEqualTo(DEFAULT_PAGE_SIZE);
                assertThat(result.getContent().size()).isEqualTo(items.size());
            }

            @DisplayName("빈 콘서트 목록")
            @Test
            void findEmptyConcerts() {
                ConcertSort sort = ConcertSort.NEWEST;
                Pageable pageable = PageRequest.of(100, DEFAULT_PAGE_SIZE);
                List<ConcertListDto> items = List.of();
                Page<ConcertListDto> concerts = new PageImpl<>(
                    items,
                    pageable,
                    items.size()
                );
                given(concertRepository.findConcerts(sort, pageable))
                    .willReturn(concerts);

                Page<ConcertListDto> result = concertService.findConcerts(sort, pageable);

                assertThat(result.getContent().size()).isEqualTo(0);
            }
        }
    }

    @DisplayName("getConcertCount")
    @Nested
    class GetConcertCount {
        @DisplayName("성공")
        @Nested
        class Success {
            @DisplayName("콘서트 수 조회")
            @Test
            void getConcertCount() {
                Long count = 100L;
                given(concertRepository.count())
                    .willReturn(count);

                Long result = concertService.getConcertCount();

                assertThat(result).isEqualTo(count);
            }
        }
    }
}
