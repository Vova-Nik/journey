package org.hillel.dto.converter;

import org.hillel.dto.dto.TripDto;
import org.hillel.persistence.entity.TripEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TripMapper {

    TripDto tripTotripDto(TripEntity trip);

    TripEntity tripDtoToTrip(TripDto trip);
}
