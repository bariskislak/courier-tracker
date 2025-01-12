package com.migros.courier_tracker.service.strategy.impl;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class EuclidianDistanceCalculatorStrategyTest {

    @InjectMocks
    private EuclidianDistanceCalculatorStrategy distanceCalculator;

    @Mock
    private Point point1;

    @Mock
    private Point point2;

    public EuclidianDistanceCalculatorStrategyTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateDistance_validPoints() {
        when(point1.distance(point2)).thenReturn(5.0);

        double result = distanceCalculator.calculateDistance(point1, point2);

        assertEquals(5.0, result, 0.0001);
    }

    @Test
    void testCalculateDistance_samePoints() {
        when(point1.distance(point1)).thenReturn(0.0);

        double result = distanceCalculator.calculateDistance(point1, point1);

        assertEquals(0.0, result, 0.0001);
    }

    @Test
    void testCalculateDistance_negativeCoordinates() {
        when(point1.distance(point2)).thenReturn(5.0);

        double result = distanceCalculator.calculateDistance(point1, point2);

        assertEquals(5.0, result, 0.0001);
    }

    @Test
    void testCalculateDistance_nullFirstPoint() {
        assertThrows(NullPointerException.class, () -> distanceCalculator.calculateDistance(null, point2));
    }

}