package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.enums.VehicleType;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class RouteDto {
    private Long id;
    private String name;
    private String stationFrom;
    private String stationTo;
    private String departurePeriod;
    private LocalTime departureTime;
    private long duration;
    private LocalTime arrivalTime;
    private VehicleType type;
    private String description;


    @Override
    public String toString() {
        return  "Route " +
                " " + name + " " +
                ", description=" + description +
                ", " + stationFrom + " -> " + stationTo +
                ", " + departurePeriod +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", " + type;
    }
}
