package com.concertticketing.api.apis.venues.service;

import com.concertticketing.api.apis.venues.domain.VenueArea;
import com.concertticketing.api.apis.venues.repository.VenueAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueAreaSearchService {
    private final VenueAreaRepository areaRepository;

    @Transactional(readOnly = true)
    public List<VenueArea> findAreas(Long layoutId) { return areaRepository.findAreasByLayoutId(layoutId); }
}
