package com.migros.courier_tracker.service;

import com.migros.courier_tracker.service.dto.LocationDTO;

import java.io.IOException;

public interface CourierService {

    void updateCourierLocation(Long id, LocationDTO locationDto) throws IOException;

    Double getTotalDistance(Long id);
}