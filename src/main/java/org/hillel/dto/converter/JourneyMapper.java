package org.hillel.dto.converter;

import org.hillel.dto.dto.JourneyDto;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.TripEntity;
import org.mapstruct.Mapper;

@Mapper
public class JourneyMapper {
    public JourneyDto journeyToJourneyDto(JourneyEntity journeyEntity) {
        if ( journeyEntity == null ) {
            return null;
        }
        JourneyDto journeyDto = new JourneyDto();
        journeyDto.setId( journeyEntity.getId() );
        journeyDto.setName( journeyEntity.getName() );
//        journeyDto.setStationFrom( journeyEntity.getStationFrom().getName() );
        journeyDto.setStationFrom( journeyEntity.getStationFrom());

//        journeyDto.setStationTo( journeyEntity.getStationTo().getName() );
        journeyDto.setStationTo( journeyEntity.getStationTo());

        journeyDto.setDepartureDate( journeyEntity.getDeparture().toString() );
        journeyDto.setArrivalDate( journeyEntity.getArrival().toString() );

        TripEntity trip = journeyEntity.getTrip();
        RouteEntity route = trip.getRoute();
        journeyDto.setRouteName(route.getName());
        journeyDto.setRouteDescript(route.getStationFrom() + " -> " + route.getStationTo());
        journeyDto.setVehicleType(trip.getVehicle().getVehicleType().toString());
        journeyDto.setVehicleName(trip.getVehicle().getName());

        return journeyDto;
    }


    public JourneyEntity journeyDtoToJourney(JourneyDto jorneyDto) {
        if ( jorneyDto == null ) {
            return null;
        }

        JourneyEntity journeyEntity = new JourneyEntity();

        journeyEntity.setId( jorneyDto.getId() );

        return journeyEntity;
    }
}
