package com.concertticketing.sellerapi.apis.venues.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.venues.domain.VenueLayout;
import com.concertticketing.sellerapi.apis.venues.repository.VenueLayoutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VenueLayoutSearchService {
    private final VenueLayoutRepository venueLayoutRepository;

    public VenueLayout getReference(Long venueLayoutId) {
        return venueLayoutRepository.getReferenceById(venueLayoutId);
    }
}
