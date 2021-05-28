package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.enums.VehicleType;

import java.sql.Time;
import java.util.Set;

@Getter
@Setter
public class RouteDto {
    private Long id;
    private String name;
    private String stationFrom;
    private String stationTo;
    private String departurePeriod;
    private Time departureTime;
    private long duration;
    private Time arrivalTime;
    private VehicleType type;
    private Set<StationEntity> stations;

    @Override
    public String toString() {
        return  "Route " +
                " " + name + " " +
                ", " + stationFrom + " -> " + stationTo +
                ", " + departurePeriod +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", " + type;
    }
}
