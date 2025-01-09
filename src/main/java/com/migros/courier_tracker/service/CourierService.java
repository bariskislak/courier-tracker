package com.migros.courier_tracker.service;

import com.migros.courier_tracker.service.dto.LocationDto;

public interface CourierService {

    void updateCourierLocation(Long id, LocationDto locationDto);

    double getTotalDistance(Long id);
}