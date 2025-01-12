package com.migros.courier_tracker.entity.repository;

import com.migros.courier_tracker.entity.dao.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {

    // Find all locations by courier ID
    List<CourierLocation> findByCourierId(Long courierId);
    
    // Find all locations by courier ID, sorted by creation date in ascending order
    List<CourierLocation> findByCourierIdOrderByAtTimeAsc(Long courierId);

}