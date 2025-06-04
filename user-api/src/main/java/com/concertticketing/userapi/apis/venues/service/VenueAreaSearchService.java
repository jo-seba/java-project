package com.concertticketing.userapi.apis.venues.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.userapi.apis.venues.domain.VenueArea;
import com.concertticketing.userapi.apis.venues.repository.VenueAreaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueAreaSearchService {
    private final VenueAreaRepository areaRepository;

    @Transactional(readOnly = true)
    public List<VenueArea> findAreas(Long layoutId) {
        return areaRepository.findAreasByLayoutId(layoutId);
    }
}
