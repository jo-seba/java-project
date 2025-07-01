package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.repository.ConcertDetailImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertDetailImageDeleteService {
    private final ConcertDetailImageRepository concertDetailImageRepository;

    public int delete(List<Long> ids) {
        return concertDetailImageRepository.deleteConcertDetailImages(ids);
    }
}
