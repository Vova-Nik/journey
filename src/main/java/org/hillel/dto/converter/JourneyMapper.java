package org.hillel.dto.converter;

import org.hillel.dto.dto.JourneyDto;
import org.hillel.persistence.entity.JourneyEntity;
import org.mapstruct.Mapper;

@Mapper
public interface JourneyMapper {

    JourneyDto vehicleToVehicleDto(JourneyEntity vehicle);

    JourneyEntity vehicleDtoToVehicle(JourneyDto vehicle);
}
