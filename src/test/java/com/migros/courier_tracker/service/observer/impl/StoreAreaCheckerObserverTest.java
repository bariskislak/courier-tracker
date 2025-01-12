package com.migros.courier_tracker.service.observer.impl;

import com.migros.courier_tracker.config.AppConfig;
import com.migros.courier_tracker.config.StoreConfig;
import com.migros.courier_tracker.service.cache.CourierCacheService;
import com.migros.courier_tracker.service.dto.StoreDTO;
import com.migros.courier_tracker.service.strategy.DistanceCalculatorStrategy;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StoreAreaCheckerObserverTest {

    @Mock
    private StoreConfig storeConfig;

    @Mock
    private AppConfig appConfig;

    @Mock
    private CourierCacheService courierCacheService;

    @Mock
    private DistanceCalculatorStrategy distanceCalculatorStrategy;

    @InjectMocks
    private StoreAreaCheckerObserver storeAreaCheckerObserver;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    public StoreAreaCheckerObserverTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOnCourierLocationUpdate_CourierWithin100m_Loggable() throws IOException {
        Long courierId = 1L;
        Point courierLocation = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(10, 10));
        StoreDTO store = new StoreDTO("Store1", geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(10.0001, 10.0001)));
        when(storeConfig.storeList()).thenReturn(List.of(store));
        when(appConfig.defaultDistanceCalculatorStrategy()).thenReturn(distanceCalculatorStrategy);
        when(distanceCalculatorStrategy.calculateDistance(any(Point.class), any(Point.class))).thenReturn(50.0);
        when(courierCacheService.isCourierLoggable(courierId, store.getName())).thenReturn(true);

        storeAreaCheckerObserver.init();
        storeAreaCheckerObserver.onCourierLocationUpdate(courierId, courierLocation);

        verify(courierCacheService).isCourierLoggable(courierId, store.getName());
    }

    @Test
    void testOnCourierLocationUpdate_CourierOutside100m_NotLogged() throws IOException {
        Long courierId = 1L;
        Point courierLocation = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(10, 10));
        StoreDTO store = new StoreDTO("Store1", geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(20, 20)));
        when(storeConfig.storeList()).thenReturn(List.of(store));
        when(appConfig.defaultDistanceCalculatorStrategy()).thenReturn(distanceCalculatorStrategy);
        when(distanceCalculatorStrategy.calculateDistance(any(Point.class), any(Point.class))).thenReturn(150.0);

        storeAreaCheckerObserver.init();
        storeAreaCheckerObserver.onCourierLocationUpdate(courierId, courierLocation);

        verify(courierCacheService, never()).setCourierLogLock(anyLong(), anyString());
    }

    @Test
    void testOnCourierLocationUpdate_CourierWithin100m_NotLoggable() throws IOException {
        Long courierId = 1L;
        Point courierLocation = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(10, 10));
        StoreDTO store = new StoreDTO("Store1", geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(10.0001, 10.0001)));
        when(storeConfig.storeList()).thenReturn(List.of(store));
        when(appConfig.defaultDistanceCalculatorStrategy()).thenReturn(distanceCalculatorStrategy);
        when(distanceCalculatorStrategy.calculateDistance(any(Point.class), any(Point.class))).thenReturn(50.0);
        when(courierCacheService.isCourierLoggable(courierId, store.getName())).thenReturn(false);

        storeAreaCheckerObserver.init();
        storeAreaCheckerObserver.onCourierLocationUpdate(courierId, courierLocation);

        verify(courierCacheService, never()).setCourierLogLock(anyLong(), anyString());
    }
}