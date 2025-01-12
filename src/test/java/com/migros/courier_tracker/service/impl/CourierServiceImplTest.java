package com.migros.courier_tracker.service.impl;

import com.migros.courier_tracker.config.AppConfig;
import com.migros.courier_tracker.entity.dao.CourierLocation;
import com.migros.courier_tracker.entity.repository.CourierLocationRepository;
import com.migros.courier_tracker.exception.ResourceNotFoundException;
import com.migros.courier_tracker.service.cache.CourierCacheService;
import com.migros.courier_tracker.service.dto.LocationDTO;
import com.migros.courier_tracker.service.observer.subject.CourierLocationSubject;
import com.migros.courier_tracker.service.strategy.DistanceCalculatorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourierServiceImplTest {

    @Mock
    private CourierLocationRepository courierLocationRepository;
    @Mock
    private CourierLocationSubject courierLocationSubject;
    @Mock
    private CourierCacheService courierCacheService;
    @Mock
    private AppConfig appConfig;
    @Mock
    private DistanceCalculatorStrategy distanceCalculatorStrategy;
    @InjectMocks
    private CourierServiceImpl courierService;

    private GeometryFactory geometryFactory;

    @BeforeEach
    public void setUp() {
        geometryFactory = new GeometryFactory();
        when(appConfig.defaultDistanceCalculatorStrategy()).thenReturn(distanceCalculatorStrategy);
        courierService.init();
    }

    @Test
    void getTotalDistance_ShouldThrowException_WhenCourierNotFound() {
        Long courierId = 1L;

        when(courierLocationRepository.findByCourierIdOrderByAtTimeAsc(courierId))
                .thenReturn(Collections.emptyList());
        when(courierCacheService.getTotalDistance(courierId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> courierService.getTotalDistance(courierId));

        verify(courierLocationRepository, times(1)).findByCourierIdOrderByAtTimeAsc(courierId);
    }

    @Test
    void getTotalDistance_ShouldReturnZero_WhenSingleLocationExists() {
        Long courierId = 1L;
        Point location = geometryFactory.createPoint(new Coordinate(29.0, 41.0));
        location.setSRID(4326);

        CourierLocation courierLocation = new CourierLocation();
        courierLocation.setCourierId(courierId);
        courierLocation.setLocation(location);

        when(courierLocationRepository.findByCourierIdOrderByAtTimeAsc(courierId))
                .thenReturn(Collections.singletonList(courierLocation));
        when(courierCacheService.getTotalDistance(courierId)).thenReturn(null);

        double result = courierService.getTotalDistance(courierId);

        assertEquals(0.0, result);

        verify(courierLocationRepository, times(1)).findByCourierIdOrderByAtTimeAsc(courierId);
    }

    @Test
    public void testGetTotalDistance() {
        Coordinate coordinate1 = new Coordinate(29.1244229, 40.9923307); // Point 1
        Point point1 = geometryFactory.createPoint(coordinate1);
        point1.setSRID(4326);

        Coordinate coordinate2 = new Coordinate(29.1161293, 40.986106); // Point 2
        Point point2 = geometryFactory.createPoint(coordinate2);
        point2.setSRID(4326);

        CourierLocation location1 = new CourierLocation();
        location1.setLocation(point1);
        CourierLocation location2 = new CourierLocation();
        location2.setLocation(point2);

        when(courierLocationRepository.findByCourierIdOrderByAtTimeAsc(1L)).thenReturn(List.of(location1, location2));
        when(distanceCalculatorStrategy.calculateDistance(any(Point.class), any(Point.class))).thenReturn(1000.0);
        when(courierCacheService.getTotalDistance(1L)).thenReturn(null);

        double totalDistance = courierService.getTotalDistance(1L);

        assertEquals(1000.0, totalDistance);
        verify(distanceCalculatorStrategy).calculateDistance(any(Point.class), any(Point.class));
    }


    @Test
    public void testUpdateCourierLocation() throws IOException {
        Long courierId = 1L;
        LocationDTO locationDto = new LocationDTO();
        locationDto.setLatitude(40.9923307);
        locationDto.setLongitude(29.1244229);
        locationDto.setAtTime(LocalDateTime.now());
        doNothing().when(courierLocationSubject).notifyObservers(any(Long.class), any(Point.class));
        when(courierLocationRepository.save(any(CourierLocation.class))).thenReturn(new CourierLocation());
        assertDoesNotThrow(() -> courierService.updateCourierLocation(courierId, locationDto));
        verify(courierLocationRepository).save(any(CourierLocation.class));
        verify(courierLocationSubject).notifyObservers(any(Long.class), any(Point.class));
    }

    @Test
    void updateCourierLocation_ShouldThrowException_WhenIOExceptionOccurs() throws IOException {
        Long courierId = 1L;
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLongitude(29.0);
        locationDTO.setLatitude(41.0);
        locationDTO.setAtTime(LocalDateTime.now());

        doThrow(IOException.class).when(courierLocationSubject).notifyObservers(anyLong(), any(Point.class));

        assertThrows(IOException.class, () -> courierService.updateCourierLocation(courierId, locationDTO));

        verify(courierLocationRepository, times(1)).save(any(CourierLocation.class));
        verify(courierLocationSubject, times(1)).notifyObservers(anyLong(), any(Point.class));
    }

}