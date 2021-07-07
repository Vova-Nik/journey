package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class StopDto {
    private Long id;
    private String name;
    private String stationName;
    private Long stationId;
    private String routeName;
    private String routeDescription;

    private Long routeId;
    private LocalTime arrival;
    private int duration;
    private LocalTime departure;
    private String description;
    private Integer dayOffset;

    @Override
    public String toString() {
        return "StopDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeDescription='" + routeDescription + '\'' +
                ", arrival=" + arrival +
                ", duration=" + duration +
                ", departure=" + departure +
                ", description='" + description + '\'' +
                ", dayOffset=" + dayOffset +
                '}';
    }


    public String show() {
        return   "route  -  " + routeName +  ",  " +
                 "    " + routeDescription + ",  " +
                 "departure  -  " + arrival;
    }
}



