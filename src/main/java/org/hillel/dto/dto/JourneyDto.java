package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import java.time.Instant;

@Getter
@Setter
public class JourneyDto {
    private Long id;
    private String name;
    private StationEntity stationFrom;
    private StationEntity stationTo;
    private Instant departure;
    private Instant arrival;
    private RouteEntity route;
}
