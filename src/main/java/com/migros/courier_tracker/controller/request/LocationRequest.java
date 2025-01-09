package com.migros.courier_tracker.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {
    private Double latitude;
    private Double longitude;
}
