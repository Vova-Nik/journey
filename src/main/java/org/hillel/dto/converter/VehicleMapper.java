package org.hillel.dto.converter;

import org.hillel.dto.dto.VehicleDto;
import org.hillel.persistence.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VehicleMapper {
    @Mapping(source = "overalCapacity", target = "capacity")
    VehicleDto vehicleToVehicleDto(VehicleEntity vehicle);

    @Mapping(source = "capacity", target = "overalCapacity")
    VehicleEntity vehicleDtoToVehicle(VehicleDto vehicle);
}
