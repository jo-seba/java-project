package com.concertticketing.api.apis.v1.concerts.repository;

import com.concertticketing.api.apis.v1.concerts.domain.Concert;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository {
    Optional<Concert> findById(Long id);
    List<Concert> findAll(int page, int size);
}
