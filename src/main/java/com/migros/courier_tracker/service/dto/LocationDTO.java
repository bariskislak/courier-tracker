package com.migros.courier_tracker.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LocationDTO {
    private double longitude;
    private double latitude;
    private LocalDateTime atTime;
}
