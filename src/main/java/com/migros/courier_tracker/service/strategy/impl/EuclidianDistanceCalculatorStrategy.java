package com.migros.courier_tracker.service.strategy.impl;

import com.migros.courier_tracker.service.strategy.DistanceCalculatorStrategy;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component("euclidianDistanceCalculator")
public class EuclidianDistanceCalculatorStrategy implements DistanceCalculatorStrategy {
    @Override
    public double calculateDistance(Point point1, Point point2) {
        return point1.distance(point2);
    }
}
