package com.migros.courier_tracker.mapper;

import com.migros.courier_tracker.controller.request.LocationRequest;
import com.migros.courier_tracker.service.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    @Mapping(target = "id", ignore = true)
    LocationDto toCourier(LocationRequest courierRequest);
}
