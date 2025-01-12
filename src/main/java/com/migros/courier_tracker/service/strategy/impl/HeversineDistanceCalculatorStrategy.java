package com.migros.courier_tracker.service.strategy.impl;

import com.migros.courier_tracker.service.strategy.DistanceCalculatorStrategy;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component( "heversineDistanceCalculator")
public class HeversineDistanceCalculatorStrategy implements DistanceCalculatorStrategy {
    private static final double EARTH_RADIUS_KM = 6371.0;

    @Override
    public double calculateDistance(Point point1, Point point2) {
        double lat1 = Math.toRadians(point1.getY());
        double lon1 = Math.toRadians(point1.getX());
        double lat2 = Math.toRadians(point2.getY());
        double lon2 = Math.toRadians(point2.getX());

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c * 1000;
    }
}
