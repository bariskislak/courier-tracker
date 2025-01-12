package com.migros.courier_tracker.mapper;

import com.migros.courier_tracker.controller.request.CourierLocationRequest;
import com.migros.courier_tracker.service.dto.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(source = "atTime", target = "atTime")
    LocationDTO toDTO(CourierLocationRequest courierRequest);
}