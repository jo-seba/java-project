package com.concertticketing.api.apis.concerts.service;

import com.concertticketing.api.apis.concerts.domain.Concert;
import com.concertticketing.api.apis.concerts.repository.ConcertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {
    @InjectMocks
    private ConcertSearchService concertService;

    @Mock
    private ConcertRepository concertRepository;

    @Test
    @DisplayName("콘서트 디테일 존재 O")
    void findById_found() {
//        Long concertId = 1L;
//        Concert testConcert = createConcert(concertId);

//        when(concertRepository.findById(concertId))
//                .thenReturn(Optional.of(testConcert));

//        Optional<Concert> result = concertService.findById(concertId);
//
//        assertTrue(result.isPresent());
//        assertEquals(testConcert.getId(), result.get().getId());
    }

    @Test
    @DisplayName("콘서트 디테일 존재 X")
    void findById_notFound() {
//        when(concertRepository.findById(anyLong()))
//                .thenReturn(Optional.empty());

//        Optional<Concert> result = concertService.findById(1L);
//
//        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("콘서트 리스트 존재 O")
    void findAll_found() {
//        List<Concert> testConcerts = List.of(createConcert(1L), createConcert(2L), createConcert(3L));
//        int page = 1, size = 10;

//        when(concertRepository.findAll(page, size))
//                .thenReturn(testConcerts);
//
//        List<Concert> result = concertService.findAll(page, size);
//
//        assertFalse(result.isEmpty());
//        assertEquals(testConcerts.size(), result.size());
    }

    @Test
    @DisplayName("콘서트 리스트 존재 X")
    void findAll_notFound() {
//        when(concertRepository.findAll(anyInt(), anyInt()))
//                .thenReturn(List.of());
//
//        List<Concert> result = concertService.findAll(1, 10);
//
//        assertTrue(result.isEmpty());
    }

//    private Concert createConcert(Long concertId) {
//        return Concert.builder()
//                .id(concertId)
//                .title("테스트 콘서트")
//                .description("테스트 설명")
//                .startedAt(LocalDateTime.of(2025, 5, 1, 0, 0))
//                .endedAt(LocalDateTime.of(2025, 5, 4, 0, 0))
//                .bookingStartedAt(LocalDateTime.of(2025, 4, 1, 10, 0))
//                .bookingEndedAt(LocalDateTime.of(2025, 4, 30, 18, 0))
//                .build();
//    }
}