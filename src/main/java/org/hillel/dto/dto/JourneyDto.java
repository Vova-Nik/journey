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
    private String departureDate;
    private String arrivalDate;
    private String departureTime;
    private String arrivalTime;
    private String routeName;
    private String routeDescript;
    private String vehicleType;
    private String vehicleName;

    public JourneyDto(TripEntity trip, String stationFrom, String stationTo) {
//        RouteEntity route = trip.getRoute();
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.departureDate = trip.getDepartureDate().toString();

//        this.name = route.getName();
//        this.routeName = route.getName();
//        this.routeDescript = trip.getDescription();
//        this.vehicleType = trip.getVehicle().getVehicleType().toString();
//        this.vehicleName = trip.getVehicle().getName();
//        this.departureDate = trip.getDepartureDate().toString();

    }

    @Override
    public String toString() {
        return "JourneyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", departure='" + departureDate + " - " + departureTime +'\'' +
                ", arrival='" + arrivalDate + " - " + arrivalTime + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeDescription='" + routeDescript + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                '}';
    }
}
