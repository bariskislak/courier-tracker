package com.migros.courier_tracker.controller;

import com.migros.courier_tracker.controller.request.CourierLocationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/courier")
public interface CourierController {

    @PostMapping("/{id}/location")
    void updateCourierLocation(@PathVariable Long id, @RequestBody CourierLocationRequest courierLocationRequest) throws IOException;

    @GetMapping("/{id}/total-distance")
    ResponseEntity<Double> getTotalDistance(@PathVariable Long id);
}
