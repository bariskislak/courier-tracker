package com.migros.courier_tracker.controller.impl;

import com.migros.courier_tracker.controller.CourierController;
import com.migros.courier_tracker.controller.request.LocationRequest;
import com.migros.courier_tracker.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourierControllerImpl implements CourierController {

    private final CourierService courierService;

    @Override
    public void updateCourierLocation(Long id, LocationRequest locationRequest) {
        //TODO: create row for update
    }

    @Override
    public ResponseEntity<Double> getTotalDistance(Long id) {
        // TODO: after total distance calculation can be store at cache if any data update
        //invalidate it
        return ResponseEntity.ok(0.0);
    }
}