package com.migros.courier_tracker.config;

import com.migros.courier_tracker.service.strategy.DistanceCalculatorStrategy;
import com.migros.courier_tracker.service.strategy.impl.EuclidianDistanceCalculatorStrategy;
import com.migros.courier_tracker.service.strategy.impl.HeversineDistanceCalculatorStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${application.default-strategy:haversine}")
    private String defaultStrategy;

    @Bean
    public DistanceCalculatorStrategy defaultDistanceCalculatorStrategy() {
        return switch (defaultStrategy.toLowerCase()) {
            case "euclidean" -> new EuclidianDistanceCalculatorStrategy();
            default -> new HeversineDistanceCalculatorStrategy();
        };
    }
}
