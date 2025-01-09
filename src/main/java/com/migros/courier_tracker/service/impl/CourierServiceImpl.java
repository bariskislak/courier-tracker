package com.migros.courier_tracker.service.impl;

import com.migros.courier_tracker.service.CourierService;
import com.migros.courier_tracker.service.dto.LocationDto;
import org.springframework.stereotype.Service;

@Service
public class CourierServiceImpl implements CourierService {


    @Override
    public void updateCourierLocation(Long id, LocationDto locationDto) {
        //TODO: create db log
    }

    @Override
    public double getTotalDistance(Long id) {
        //TODO: search postgresql gis extension for easy calculation
        return 0;
    }
}
