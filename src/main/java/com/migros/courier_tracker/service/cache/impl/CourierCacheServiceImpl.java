package com.migros.courier_tracker.service.cache.impl;

import com.migros.courier_tracker.service.cache.CourierCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CourierCacheServiceImpl implements CourierCacheService {

    private final RedisTemplate<String, Double> redisTotalDistanceTemplate;
    private final RedisTemplate<String, String> redisCourierStoreTemplate;

    @Override
    public Double getTotalDistance(Long courierId) {
        return redisTotalDistanceTemplate.opsForValue()
                .get("totalDistance:" + courierId);
    }

    @Override
    public void setTotalDistance(Long id, double distance) {
        redisTotalDistanceTemplate.opsForValue().set("totalDistance:" + id, distance);
    }

    @Override
    public void invalidateTotalDistance(Long courierId) {
        redisTotalDistanceTemplate.delete("totalDistance:" + courierId);
    }

    @Override
    public boolean isCourierLoggable(Long courierId, String storeName) {
        return redisCourierStoreTemplate.opsForValue().get(generateStoreCourierKey(courierId, storeName)) == null;
    }

    @Override
    public void setCourierLogLock(Long courierId, String storeName) {
        redisCourierStoreTemplate.opsForValue()
                .set(generateStoreCourierKey(courierId, storeName), "", 60, TimeUnit.SECONDS);
    }

    private String generateStoreCourierKey(Long courierId, String storeName) {
        return "courier:" + courierId + ":" + storeName.replaceAll(" ", "_").toLowerCase();
    }
}
