package com.migros.courier_tracker.service.observer.impl;

import com.migros.courier_tracker.config.AppConfig;
import com.migros.courier_tracker.config.StoreConfig;
import com.migros.courier_tracker.service.observer.CourierObserver;
import com.migros.courier_tracker.service.strategy.DistanceCalculatorStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StoreAreaCheckerObserver implements CourierObserver {
    private final StoreConfig storeConfig;
    private final AppConfig appConfig;
    private DistanceCalculatorStrategy distanceCalculationStrategy;

    @PostConstruct
    public void init() {
        this.distanceCalculationStrategy = appConfig.defaultDistanceCalculatorStrategy();
    }

    @Override
    public void onCourierLocationUpdate(Long courierId, Point location) throws IOException {
        storeConfig.storeList().forEach(store -> {
            double distance = distanceCalculationStrategy.calculateDistance(location, store.getLocation());
            if (distance < 100) {
                System.out.println("Courier " + courierId + " is within 100m of store: " + store.getName());
            }
        });
    }
}
