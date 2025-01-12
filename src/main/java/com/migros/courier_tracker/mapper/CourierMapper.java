package com.migros.courier_tracker.mapper;

import com.migros.courier_tracker.controller.request.CourierLocationRequest;
import com.migros.courier_tracker.service.dto.LocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    LocationDTO toDTO(CourierLocationRequest courierRequest);
}