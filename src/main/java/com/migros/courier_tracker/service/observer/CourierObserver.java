package com.migros.courier_tracker.service.observer;

import org.locationtech.jts.geom.Point;

import java.io.IOException;

public interface CourierObserver {
    void onCourierLocationUpdate(Long courierId, Point location) throws IOException;
}
