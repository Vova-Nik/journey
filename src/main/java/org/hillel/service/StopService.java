package org.hillel.service;


import net.bytebuddy.agent.builder.AgentBuilder;
import org.hillel.persistence.entity.AbstractEntity;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.jpa.repository.RouteJPARepository;
import org.hillel.persistence.jpa.repository.StationJPARepository;
import org.hillel.persistence.jpa.repository.StopJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            System.out.println("StopService not valid StopEntity");
            return null;
        }
        RouteEntity route;
        StationEntity station;
        try {
            assert entity.getRoute().getId() != null;
            route = routeJPARepository.findById(entity.getRoute().getId()).orElseThrow(IllegalArgumentException::new);
            assert entity.getStation().getId() != null;
            station = stationJPARepository.findById(entity.getStation().getId()).orElseThrow(IllegalArgumentException::new);
        } catch (AssertionError | IllegalArgumentException e) {
            System.out.println("StopService not valid StopEntity" + e.getCause());
            return null;
        }
        StopEntity stopEntity = new StopEntity(route, station, entity.getArrival(), entity.getDuration(), entity.getDayOffset());
        return stopJPARepository.save(stopEntity);
    }

    @Transactional
    public List<Long> findRouteByStops(String stationFrom, String stationTo) {
        List<Long> routes = stopJPARepository.findRoute(stationFrom, stationTo);
        if (routes.size() == 0) {
            return routes;
        }
//        List<Long> result =  routes.stream()
        return routes;
    }

    @Transactional
    public Set<StopEntity> findAllByRoute(final RouteEntity routeEntity) {
        RouteEntity route = routeJPARepository.findOneByName(routeEntity.getName());
        return stopJPARepository.findAllByRoute(route);
    }

    @Transactional
    public List<StopEntity> findAllByRouteSorted(final RouteEntity routeEntity) {
        RouteEntity route = routeJPARepository.findOneByName(routeEntity.getName());
        stopJPARepository.findAllByRoute(route);
        List<StopEntity> stops = new ArrayList<>(stopJPARepository.findAllByRoute(route));
        Collections.sort(stops);
        return stops;
    }

    @Transactional
    public Set<RouteEntity> findAllRoutesByStation(final StationEntity stationEntity) {
        Set<StopEntity> stops = stopJPARepository.findStopsByStation(stationEntity);
        Set<Long> routeIds = stops.stream()
                .map(stop -> stop.getRoute().getId())
                .collect(Collectors.toSet());
        Set<RouteEntity> routes = routeJPARepository.findByIds(routeIds);
        return routes;
    }

    @Transactional
    public Set<StopEntity> findAllByStation(final StationEntity stationEntity) {
//        Set<StopEntity> stops = stopJPARepository.findStopsByStation(stationEntity);
        return stopJPARepository.findStopsByStation(stationEntity);
    }

    @Override
    boolean isValid(final StopEntity entity) {
        return entity.getRoute().isValid() && entity.getStation().isValid() && entity.getArrival() != null;
    }
}
