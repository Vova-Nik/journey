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
@NoArgsConstructor
public class JourneyDto {
    private boolean correct;
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
    private int freePlaces;
    private String comment;

    public JourneyDto(TripEntity trip, String stationFrom, String stationTo) {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.departureDate = trip.getDepartureDate().toString();
    }

    public JourneyDto(String comment) {
        correct = false;
        this.comment = comment;
        if (comment.length() < 3)
            this.comment = "There is no throw variant. Try finding over transit stations, such as Kyiv, Zhmerynka e.t.c.";
    }

    @Override
    public String toString() {
        return "JourneyDto{" +
                "correct=" + correct +
                ", name='" + name + '\'' +
                ", stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeDescript='" + routeDescript + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", freePlaces=" + freePlaces +
                ", comment='" + comment + '\'' +
                '}';
    }
}
