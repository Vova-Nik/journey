package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.enums.VehicleType;

@Getter
@Setter
public class VehicleDto {
    private Long id;
    private String name;
    private int capacity;
    private VehicleType vehicleType;
}
