package org.hillel.service;


import net.bytebuddy.agent.builder.AgentBuilder;
import org.hillel.exceptions.StopAPIException;
import org.hillel.persistence.entity.AbstractEntity;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.jpa.repository.RouteJPARepository;
import org.hillel.persistence.jpa.repository.StationJPARepository;
import org.hillel.persistence.jpa.repository.StopJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("StopService")
public class StopService extends EntityServiceImplementation<StopEntity, Long> {

    private StopJPARepository stopJPARepository;
    @Autowired
    private RouteJPARepository routeJPARepository;
    @Autowired
    private StationJPARepository stationJPARepository;

    @Autowired
    public StopService(StopJPARepository stopJPARepository) {
        super(StopEntity.class, stopJPARepository);
        this.stopJPARepository = stopJPARepository;
    }

    @Override
    @Transactional
    public StopEntity save(final StopEntity entity) {
        if (Objects.isNull(entity) || !entity.isValid()) {
            throw new StopAPIException("StopService.save not valid StopEntity");
        }
        RouteEntity route;
        StationEntity station;
        try {
            assert entity.getRoute().getId() != null;
            route = routeJPARepository.findById(entity.getRoute().getId()).orElseThrow(IllegalArgumentException::new);
            assert entity.getStation().getId() != null;
            station = stationJPARepository.findById(entity.getStation().getId()).orElseThrow(IllegalArgumentException::new);
        } catch (AssertionError | IllegalArgumentException e) {
            throw new StopAPIException("StopService.save not valid Route or Station");
        }
//        StopEntity stopEntity = new StopEntity(route, station, entity.getArrival(), entity.getDuration(), entity.getDayOffset());
        return stopJPARepository.save(entity);
    }

    @Transactional
    public void update(final StopEntity entity) {
        if (Objects.isNull(entity) || !entity.isValid() || entity.getId() == null) {
            throw new StopAPIException("StopService.update not valid StopEntity");
        }
        stopJPARepository.findById(entity.getId());
        stopJPARepository.update(
                entity.getId(),
                entity.isActive(),
                entity.getArrival(),
                entity.getDeparture(),
                entity.getDescription(),
                entity.getStaying(),
                entity.getName(),
                entity.getRoute().getId(),
                entity.getStation().getId(),
                entity.getSecOffset(),
                entity.getSecOffset()
        );
    }

//    @Transactional
//    public List<Long> findRouteByStops(final String stationFrom, final String stationTo) {
//        if (StringUtils.isEmpty(stationFrom) || StringUtils.isEmpty(stationTo)) {
//            throw new StopAPIException("StopService.findRouteByStops incorrect station name");
//        }
//        List<RouteEntity> routes = stopJPARepository.findRoute(stationFrom, stationTo);
//        if (routes.size() == 0) {
//            throw new StopAPIException("StopService.findRouteByStops can not find route by stops");
//        }
//        return routes.stream().map(AbstractEntity::getId).collect(Collectors.toList());
//    }

    @Transactional
    public Set<StopEntity> findAllByRoute(final RouteEntity routeEntity) {
        if (!routeEntity.isValid()) {
            throw new StopAPIException("StopService.findAllByRoute route is not valid");
        }
        RouteEntity route = routeJPARepository.findOneByName(routeEntity.getName());
        return stopJPARepository.findAllByRoute(route);
    }

    @Transactional
    public List<StopEntity> findAllByRouteSorted(final RouteEntity routeEntity) {
        if (!routeEntity.isValid()) {
            throw new StopAPIException("StopService.findAllByRouteSorted route is not valid");
        }
        RouteEntity route = routeJPARepository.findOneByName(routeEntity.getName());
        stopJPARepository.findAllByRoute(route);
        List<StopEntity> stops = new ArrayList<>(stopJPARepository.findAllByRoute(route));
        Collections.sort(stops);
        return stops;
    }

    @Transactional
    public Set<RouteEntity> findAllRoutesByStation(final StationEntity stationEntity) {
        if (!stationEntity.isValid()) {
            throw new StopAPIException("StopService.findAllRoutesByStation station is not valid");
        }
        Set<StopEntity> stops = stopJPARepository.findStopsByStation(stationEntity);
        Set<Long> routeIds = stops.stream()
                .map(stop -> stop.getRoute().getId())
                .collect(Collectors.toSet());
        Set<RouteEntity> routes = routeJPARepository.findByIds(routeIds);
        return routes;
    }

    @Transactional
    public Set<StopEntity> findAllByStation(final StationEntity stationEntity) {
        if (!stationEntity.isValid()) {
            throw new StopAPIException("StopService.findAllByStation station is not valid");
        }
//        Set<StopEntity> stops = stopJPARepository.findStopsByStation(stationEntity);
        return stopJPARepository.findStopsByStation(stationEntity);
    }

    @Override
    boolean isValid(final StopEntity entity) {
        return entity.getRoute().isValid() && entity.getStation().isValid() && entity.getArrival() != null;
    }
}
