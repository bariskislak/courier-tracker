package com.migros.courier_tracker.service.observer.impl;

import com.migros.courier_tracker.service.cache.impl.CourierCacheServiceImpl;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CourierCacheObserverTest {

    @Test
    void testOnCourierLocationUpdate_ValidInputs() {
        CourierCacheServiceImpl mockCourierCacheService = Mockito.mock(CourierCacheServiceImpl.class);
        CourierCacheObserver courierCacheObserver = new CourierCacheObserver(mockCourierCacheService);
        Long courierId = 1L;
        GeometryFactory geometryFactory = new GeometryFactory();
        Point location = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(40.0, 29.0));

        courierCacheObserver.onCourierLocationUpdate(courierId, location);

        verify(mockCourierCacheService, times(1)).invalidateTotalDistance(courierId);
    }
}