package com.migros.courier_tracker.service.cache;

public interface CourierCacheService {
    Double getTotalDistance(Long courierId);

    void setTotalDistance(Long courierId, double distance);

    void invalidateTotalDistance(Long courierId);

    boolean isCourierLoggable(Long courierId, String storeName);

    void setCourierLogLock(Long courierId, String storeName);
}
