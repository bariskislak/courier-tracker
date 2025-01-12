package com.migros.courier_tracker.service.strategy;

import org.locationtech.jts.geom.Point;


/**
 * This interface defines a strategy for calculating the distance 
 * between two geographic points represented by {@link Point} objects.
 * Implementing classes can provide specific algorithms, such as Haversine
 * or Euclidean distance, to compute the distance between the points.
 */
public interface DistanceCalculatorStrategy {
    double calculateDistance(Point point1, Point point2);
}
