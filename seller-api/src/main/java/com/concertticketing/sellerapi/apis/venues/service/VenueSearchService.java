package com.concertticketing.sellerapi.apis.venues.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.venues.domain.Venue;
import com.concertticketing.sellerapi.apis.venues.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VenueSearchService {
    private final VenueRepository venueRepository;

    public Venue getReference(Integer venueId) {
        return venueRepository.getReferenceById(venueId);
    }
}
