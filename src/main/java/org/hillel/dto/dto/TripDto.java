package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.VehicleEntity;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TripDto {
    private Long id;
    private String name;
    VehicleEntity vehicle;
//    private RouteEntity route;
    private String routeName;
    private int tickets;
    private int sold;
    private LocalDate departureDate;
    private LocalTime departureTime;

    //Calculated values
    private int freePlaces;
    private String departure;
    private String arrival;
}
