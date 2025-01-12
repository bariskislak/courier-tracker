package com.migros.courier_tracker.controller.impl;

import com.migros.courier_tracker.controller.CourierController;
import com.migros.courier_tracker.controller.request.CourierLocationRequest;
import com.migros.courier_tracker.mapper.CourierMapper;
import com.migros.courier_tracker.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CourierControllerImpl implements CourierController {

    private final CourierService courierService;
    private final CourierMapper courierMapper;

    @Override
    public void updateCourierLocation(Long id, CourierLocationRequest courierLocationRequest) throws IOException {
        courierService.updateCourierLocation(id, courierMapper.toDTO(courierLocationRequest));
    }

    @Override
    public ResponseEntity<Double> getTotalDistance(Long id) {
        // TODO: after total distance calculation can be store at cache if any data update
        return ResponseEntity.ok(courierService.getTotalDistance(id));
    }
}