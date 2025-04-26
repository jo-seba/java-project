package com.concertticketing.api.apis.v1.concerts.service;

import com.concertticketing.api.apis.v1.concerts.domain.Concert;
import com.concertticketing.api.apis.v1.concerts.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;

    public Optional<Concert> findById(Long id){
        return concertRepository.findById(id);
    }

    public List<Concert> findAll(int page, int size){
        return concertRepository.findAll(page,size);
    }
}
