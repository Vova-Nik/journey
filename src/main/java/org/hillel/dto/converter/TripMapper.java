package org.hillel.dto.converter;

import org.hillel.dto.dto.TripDto;
import org.hillel.persistence.entity.TripEntity;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.Instant;
import java.time.ZoneId;

@Component
public class TripMapper {
    public TripDto tripTotripDto(TripEntity trip) {
        if ( trip == null ) {
            return null;
        }

        TripDto tripDto = new TripDto();
        tripDto.setId( trip.getId() );
        tripDto.setName( trip.getName() );
        tripDto.setVehicle( trip.getVehicle() );
        tripDto.setRoute( trip.getRoute() );
        tripDto.setTickets( trip.getTickets() );
        tripDto.setSold( trip.getSold() );
        tripDto.setDepartureDate( trip.getDepartureDate() );
        tripDto.setFreePlaces(trip.getTickets()-trip.getSold());
        tripDto.setDeparture(trip.getDepartureDate() + " " + trip.getRoute().getDepartureTime());

        Instant arr = trip.getDepartureDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Time departureTime = trip.getRoute().getDepartureTime();
        int secsDeparture = departureTime.getHours()*3600 + departureTime.getMinutes()*60;
        arr = arr.plusSeconds(trip.getRoute().getDuration());
        arr = arr.plusSeconds(secsDeparture);
        String arrival = arr.toString().replace('T',' ');
        arrival = arrival.replace('Z',' ');
        tripDto.setArrival(arrival);
        return tripDto;
    }


    public TripEntity tripDtoToTrip(TripDto trip) {
        if ( trip == null ) {
            return null;
        }

        TripEntity tripEntity = new TripEntity();
        tripEntity.setId( trip.getId() );
        return tripEntity;
    }

}
