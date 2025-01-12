package com.migros.courier_tracker.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourierLocationRequest {
    private double longitude;
    private double latitude;
    private LocalDateTime atTime;
}
