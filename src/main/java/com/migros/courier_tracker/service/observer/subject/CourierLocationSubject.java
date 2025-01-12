package com.migros.courier_tracker.service.observer.subject;

import com.migros.courier_tracker.service.observer.CourierObserver;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierLocationSubject {
    private final List<CourierObserver> observers;

    public void notifyObservers(Long courierId, Point location) throws IOException {
        for (CourierObserver courierObserver : observers) {
            courierObserver.onCourierLocationUpdate(courierId, location);
        }

    }
}
