package org.hillel.dto.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.enums.StationType;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class StationDto {
    private Long id;
    private String name;
    private Double longitude;
    private Double latitude;
    private String description;

    private String foundationYear;
    private StationType stationType;
    private Set<RouteEntity> routes;

    @Override
    public String toString() {
        return "Station " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", stationType=" + stationType;
    }
}
