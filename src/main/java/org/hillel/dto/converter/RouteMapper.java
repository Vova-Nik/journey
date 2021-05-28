package org.hillel.dto.converter;

import org.hillel.dto.dto.RouteDto;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Mapper
@Component
public class RouteMapper {

    public RouteDto routeToRouteDto(RouteEntity route) {
        if ( route == null ) {
            return null;
        }

        RouteDto routeDto = new RouteDto();

        routeDto.setId( route.getId() );
        routeDto.setName( route.getName() );
        routeDto.setStationFrom( route.getStationFrom() );
        routeDto.setStationTo( route.getStationTo() );
        routeDto.setDeparturePeriod( route.getDeparturePeriod() );
        routeDto.setDepartureTime( route.getDepartureTime() );
        if ( route.getDuration() != null ) {
            routeDto.setDuration( route.getDuration() );
        }
        routeDto.setArrivalTime( route.getArrivalTime() );
        routeDto.setType( route.getType() );
        List<StationEntity> list = route.getStations();
        if ( list != null ) {
            routeDto.setStations( new HashSet<StationEntity>( list ) );
        }

        return routeDto;
    }


    public RouteEntity RouteDtoToRoute(RouteDto route) {
        if ( route == null ) {
            return null;
        }

        RouteEntity routeEntity = new RouteEntity();

        routeEntity.setId( route.getId() );
        routeEntity.setName( route.getName() );
        routeEntity.setStationFrom( route.getStationFrom() );
        routeEntity.setStationTo( route.getStationTo() );
        routeEntity.setDeparturePeriod( route.getDeparturePeriod() );
        routeEntity.setDepartureTime( route.getDepartureTime() );
        routeEntity.setDuration( route.getDuration() );
        routeEntity.setArrivalTime( route.getArrivalTime() );
        routeEntity.setType( route.getType() );
        Set<StationEntity> set = route.getStations();
        if ( set != null ) {
            routeEntity.setStations( new HashSet<StationEntity>( set ) );
        }

        return routeEntity;
    }
}
