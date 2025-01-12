package com.migros.courier_tracker.service.strategy.impl;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HeversineDistanceCalculatorStrategyTest {

    @InjectMocks
    private HeversineDistanceCalculatorStrategy distanceCalculator;

    @Mock
    private Point pointMock1;

    @Mock
    private Point pointMock2;

    public HeversineDistanceCalculatorStrategyTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the calculateDistance method which calculates the distance
     * between two geographical points using the Haversine formula.
     */
    @Test
    void calculateDistance_shouldReturnZero_whenPointsAreIdentical() {
        when(pointMock1.getX()).thenReturn(10.0);
        when(pointMock1.getY()).thenReturn(20.0);
        when(pointMock2.getX()).thenReturn(10.0);
        when(pointMock2.getY()).thenReturn(20.0);

        double result = distanceCalculator.calculateDistance(pointMock1, pointMock2);

        assertEquals(0, result, "Distance should be zero for identical points");
    }

    @Test
    void calculateDistance_shouldReturnCorrectDistance_whenPointsAreDifferent() {
        when(pointMock1.getX()).thenReturn(34.052235); // Longitude of Los Angeles
        when(pointMock1.getY()).thenReturn(-118.243683); // Latitude of Los Angeles
        when(pointMock2.getX()).thenReturn(36.169941); // Longitude of Las Vegas
        when(pointMock2.getY()).thenReturn(-115.139832); // Latitude of Las Vegas

        double result = distanceCalculator.calculateDistance(pointMock1, pointMock2);

        double expectedDistance = 367261;
        double tolerance = expectedDistance * 0.05;
        assertEquals(expectedDistance, Math.round(result), tolerance, "Distance between Los Angeles and Las Vegas should be approximately 367261 meters within 5% tolerance");
    }

    @Test
    void calculateDistance_shouldHandlePointsNearPoles() {
        when(pointMock1.getX()).thenReturn(0.0);
        when(pointMock1.getY()).thenReturn(89.9999); // Near North Pole
        when(pointMock2.getX()).thenReturn(0.0);
        when(pointMock2.getY()).thenReturn(89.9998); // Slightly South of first point

        double result = distanceCalculator.calculateDistance(pointMock1, pointMock2);

        assertEquals(11, Math.round(result), "Distance between points near poles should be calculated correctly");
    }

    @Test
    void calculateDistance_shouldHandlePointsOnOppositeSidesOfEarth() {
        when(pointMock1.getX()).thenReturn(0.0);
        when(pointMock1.getY()).thenReturn(0.0); // Equator and Prime Meridian
        when(pointMock2.getX()).thenReturn(180.0);
        when(pointMock2.getY()).thenReturn(0.0); // Opposite side of the globe

        double result = distanceCalculator.calculateDistance(pointMock1, pointMock2);

        assertEquals(20015000, Math.round(result), 1007500, "Distance between opposite points on the globe should be approximately 20015 km within 5% tolerance");
    }
}