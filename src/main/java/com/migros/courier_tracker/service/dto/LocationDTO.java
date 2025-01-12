package com.migros.courier_tracker.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationDTO {
    private double longitude;
    private double latitude;
    private LocalDateTime atTime;
}
