package org.hillel.dto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.TripEntity;

import java.time.Instant;

@Getter
@Setter
//@Builder
@NoArgsConstructor
public class JourneyDto {
    private Long id;
    private String name;
    private String stationFrom;
    private String stationTo;
    private String departure;
    private String arrival;
    private String routeName;
    private String routeDescript;
    private String vehicleType;
    private String vehicleName;

    public JourneyDto(TripEntity trip, String stationFrom, String stationTo) {
        RouteEntity route = trip.getRoute();
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.name = route.getName();
        departure = route.toString();
        arrival = trip.getArrival().toString();
        routeName = route.getName();
        routeDescript = trip.getDescription();
        vehicleType = trip.getVehicle().getVehicleType().toString();
        vehicleName = trip.getVehicle().getName();
    }

}
