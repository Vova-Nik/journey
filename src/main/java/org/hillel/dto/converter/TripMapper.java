package org.hillel.dto.converter;

import org.hillel.dto.dto.TripDto;
import org.hillel.persistence.entity.TripEntity;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
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
//        tripDto.setRoute( trip.getRoute() );
        tripDto.setRouteName(trip.getRoute().getName());
        tripDto.setTickets( trip.getTickets() );
        tripDto.setSold( trip.getSold() );
        tripDto.setDepartureDate( trip.getDepartureDate() );
        tripDto.setFreePlaces(trip.getTickets()-trip.getSold());
        tripDto.setDeparture(trip.getDepartureDate() + " " + trip.getRoute().getDepartureTime());

        Instant depart = trip.getDepartureDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        int secsDeparture = trip.getRoute().getDepartureTime().toSecondOfDay();
        depart = depart.plusSeconds(secsDeparture);
        String departure = depart.toString().replace('T',' ').replace('Z', ' ');
        tripDto.setDeparture(departure);
        Instant arriv = depart.plusSeconds(trip.getRoute().getDuration());
        String arrival = arriv.toString().replace('T',' ').replace('Z', ' ');
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
