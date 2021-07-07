package org.hillel.dto.converter;

import org.hillel.dto.dto.StopDto;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.springframework.stereotype.Component;

@Component
public class StopMapper {

    public StopDto stopToStopDto(StopEntity stopEntity) {
        if (stopEntity == null) {
            return null;
        }

        StopDto stopDto = new StopDto();
        stopDto.setId(stopEntity.getId());
        stopDto.setStationName(stopEntity.getStation().getName());
        stopDto.setStationId(stopEntity.getStation().getId());
        stopDto.setName(stopEntity.getName());
        stopDto.setRouteName(stopEntity.getRoute().getName());
        stopDto.setRouteId(stopEntity.getRoute().getId());
        stopDto.setRouteDescription(stopEntity.getRoute().getDescription());
        stopDto.setArrival(stopEntity.getArrival());
        stopDto.setDuration(stopEntity.getDuration());
        stopDto.setDeparture(stopEntity.getDeparture());
        stopDto.setDescription(stopEntity.getDescription());
        stopDto.setDayOffset(stopEntity.getDayOffset());

//        LocalDateTime ldt = LocalDateTime.ofInstant(station.getFoundation(), ZoneId.of("Europe/Kiev"));
//        stationDto.setFoundationYear(String.valueOf(ldt.getYear()));

        return stopDto;
    }

    public StopEntity stopDtoDtoToStop(StopDto stopDto) {
        if (stopDto == null) {
            return null;
        }
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId(stopDto.getRouteId());
        routeEntity.setName(stopDto.getRouteName());
        StationEntity stationEntity = new StationEntity();
        stationEntity.setId(stopDto.getStationId());
        stationEntity.setName(stopDto.getStationName());
        return new  StopEntity(routeEntity, stationEntity, stopDto.getArrival(), stopDto.getDuration(), stopDto.getDayOffset());
    }
}