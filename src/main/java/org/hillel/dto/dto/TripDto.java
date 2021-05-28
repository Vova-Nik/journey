package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.VehicleEntity;
import java.time.LocalDate;

@Getter
@Setter
public class TripDto {
    private Long id;
    private String name;
    VehicleEntity vehicle;
    private RouteEntity route;
    private int tickets;
    private int sold;
    private LocalDate departureDate;
}
