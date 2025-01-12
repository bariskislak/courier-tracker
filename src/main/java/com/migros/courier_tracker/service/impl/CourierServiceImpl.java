package com.migros.courier_tracker.service.impl;

import com.migros.courier_tracker.config.AppConfig;
import com.migros.courier_tracker.entity.dao.CourierLocation;
import com.migros.courier_tracker.entity.repository.CourierLocationRepository;
import com.migros.courier_tracker.service.CourierService;
import com.migros.courier_tracker.service.dto.LocationDTO;
import com.migros.courier_tracker.service.observer.subject.CourierLocationSubject;
import com.migros.courier_tracker.service.strategy.DistanceCalculatorStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import com.migros.courier_tracker.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierLocationRepository courierLocationRepository;
    private final CourierLocationSubject courierLocationSubject;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final AppConfig appConfig;
    private DistanceCalculatorStrategy distanceStrategy;

    @PostConstruct
    public void init() {
        this.distanceStrategy = appConfig.defaultDistanceCalculatorStrategy();
    }

    @Override
    public void updateCourierLocation(Long id, LocationDTO locationDto) throws IOException {
        Coordinate coordinate = new Coordinate(locationDto.getLongitude(), locationDto.getLatitude());
        Point location = geometryFactory.createPoint(coordinate);
        location.setSRID(4326); // WGS84 geographic reference system

        CourierLocation courierLocation = new CourierLocation();
        courierLocation.setCourierId(id);
        courierLocation.setLocation(location);
        courierLocation.setAtTime(locationDto.getAtTime());

        courierLocationRepository.save(courierLocation);
        courierLocationSubject.notifyObservers(id, location);
    }


    @Override
    public double getTotalDistance(Long id) {
        List<CourierLocation> locations = courierLocationRepository.findByCourierIdOrderByAtTimeAsc(id);

        if (locations.isEmpty()) {
            throw new ResourceNotFoundException("Courier with ID " + id + " not found.");
        }

        if (locations.size() < 2) {
            return 0.0; // Not enough locations to calculate the distance
        }

        return IntStream.range(1, locations.size())
                .mapToDouble(i -> {
                    Point currentLocation = locations.get(i).getLocation();
                    Point previousLocation = locations.get(i - 1).getLocation();
                    return distanceStrategy.calculateDistance(currentLocation, previousLocation); // Distance in meters
                })
                .sum();
    }

}
