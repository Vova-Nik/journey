package org.hillel.dto.converter;

import org.hillel.dto.dto.StationDto;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Component
public class StationMapper {

    public StationDto stationToStationDto(StationEntity station) {
        if (station == null) {
            return null;
        }

        StationDto stationDto = new StationDto();

        stationDto.setId(station.getId());
        stationDto.setName(station.getName());
        stationDto.setLongitude(station.getLongitude());
        stationDto.setLatitude(station.getLatitude());
        stationDto.setDescription(station.getDescription());
        stationDto.setStationType(station.getStationType());
        LocalDateTime ldt = LocalDateTime.ofInstant(station.getFoundation(), ZoneId.of("Europe/Kiev"));
        stationDto.setFoundationYear(String.valueOf(ldt.getYear()));

        Set<RouteEntity> set = station.getRoutes();
        if (set != null) {
            stationDto.setRoutes(new HashSet<RouteEntity>(set));
        }

        return stationDto;
    }

    public StationEntity stationDtoToStation(StationDto station) {
        if (station == null) {
            return null;
        }

        StationEntity stationEntity = new StationEntity();

        stationEntity.setId(station.getId());
        stationEntity.setName(station.getName());
        stationEntity.setLongitude(station.getLongitude());
        stationEntity.setLatitude(station.getLatitude());
        stationEntity.setDescription(station.getDescription());

        stationEntity.setFoundation(Instant.parse(station.getFoundationYear()+"-00-00T00:00:00.00Z"));

        stationEntity.setStationType(station.getStationType());
        Set<RouteEntity> set = station.getRoutes();
        if (set != null) {
            stationEntity.setRoutes(new HashSet<RouteEntity>(set));
        }

        return stationEntity;
    }
}
