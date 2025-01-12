package com.migros.courier_tracker.service.observer.impl;

import com.migros.courier_tracker.service.cache.impl.CourierCacheServiceImpl;
import com.migros.courier_tracker.service.observer.CourierObserver;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierCacheObserver implements CourierObserver {

    private final CourierCacheServiceImpl courierCacheService;

    @Override
    public void onCourierLocationUpdate(Long courierId, Point location) {
        courierCacheService.invalidateTotalDistance(courierId);
    }
}
