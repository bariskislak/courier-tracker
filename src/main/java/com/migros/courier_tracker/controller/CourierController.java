package com.migros.courier_tracker.controller;

import com.migros.courier_tracker.controller.request.LocationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courier")
public interface CourierController {

    @PutMapping("/{id}/location")
    void updateCourierLocation(@PathVariable Long id, @RequestBody LocationRequest locationRequest);

    @GetMapping("/{id}/total-distance")
    ResponseEntity<Double> getTotalDistance(@PathVariable Long id);
}
