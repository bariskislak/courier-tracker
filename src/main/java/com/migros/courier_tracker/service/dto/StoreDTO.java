package com.migros.courier_tracker.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
public class StoreDTO {
    private String name;

    private Point location;
}
