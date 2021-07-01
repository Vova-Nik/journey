package org.hillel.service;

import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.TripEntity;
import org.hillel.persistence.entity.enums.VehicleType;
import org.hillel.persistence.jpa.repository.RouteJPARepository;
import org.hillel.persistence.jpa.repository.StationJPARepository;
import org.hillel.persistence.jpa.repository.specification.RouteSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;

@Service("TrainRouteService")
public class RouteService extends EntityServiceImplementation<RouteEntity, Long> {

    private final RouteJPARepository routeRepository;
    private final StationJPARepository stationRepository;

    @Autowired
    public RouteService(final RouteJPARepository routeRepository, StationJPARepository stationRepository) {
        super(RouteEntity.class, routeRepository);
        this.routeRepository = routeRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    boolean isValid(RouteEntity entity) {
        return entity.isValid();
    }

    @Transactional(readOnly = true)
    public boolean containsStation(final Long routeId, final StationEntity station) {
        if (Objects.isNull(station) || Objects.isNull(routeId)) return false;
        RouteEntity route = routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("RouteService.containsStation can not find route"));
        return route.containsStation(station);
    }

    @Transactional
    public void addStation(final Long routeId, final StationEntity station) {
        if (Objects.isNull(station) || Objects.isNull(routeId) || !station.isValid())
            throw new IllegalArgumentException("RouteService addStation bad data");
        RouteEntity route = routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("RouteService.containsStation can not find route"));
        if (route.getStations().contains(station)) return;
        route.addStation(station);
        routeRepository.save(route);
    }

    @Transactional
    public void removeStation(final Long routeId, final StationEntity station) {
        if (Objects.isNull(station) || Objects.isNull(routeId))
            throw new IllegalArgumentException("RouteService addStation bad data");
        RouteEntity route = routeRepository.findById(routeId).orElseThrow(() -> new IllegalArgumentException("RouteService.containsStation can not find route"));
        if (!route.getStations().contains(station)) return;
        route.removeStation(station);
        routeRepository.save(route);
    }

    @Transactional
    public RouteEntity findBystations(String stationFrom, String stationTo, VehicleType vehicle) {
        if (Objects.isNull(stationRepository.findOneByName(stationFrom)))
            throw new IllegalArgumentException("RouteService.findBystations station \"" + stationFrom + "\" does not exist");
        if (Objects.isNull(stationRepository.findOneByName(stationFrom)))
            throw new IllegalArgumentException("RouteService.findBystations station \"" + stationTo + "\" does not exist");

        RouteEntity route = new RouteEntity(stationFrom, stationTo, vehicle);
        Example<RouteEntity> example = Example.of(route);
        List<RouteEntity> routes = this.findAllByExample(example);
        return routes.get(0);
    }
    @Transactional
    public List<StationEntity> getStationsOnRoute(Long id) {
        RouteEntity route = findById(id);
        List<StationEntity> stations = route.getStations();
        return stations;
    }

    @Transactional(readOnly = true)
    public List<RouteEntity> getFiltered(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        checkInput(param);
        return routeRepository.findAll(RouteSpecification.byQueryParam(param));
    }

    @Transactional(readOnly = true)
    public Page<RouteEntity> getFilteredPaged(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        param = checkInput(param);
        PageRequest page = PageRequest.of(param.getPageNumber(), param.getPageSize());
        return routeRepository.findAll(RouteSpecification.byQueryParam(param), page);
    }

    private QueryParam checkInput(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        Field[] allFields = RouteEntity.class.getDeclaredFields();
        String column = param.getSortColumn();
        boolean checked = Arrays.stream(allFields).anyMatch(field ->
                column.equals(field.getName()));
        if (column.equals("id"))
            checked = true;
        if (!checked)
            throw new IllegalArgumentException("Insufficient column name = \""+ column + "\" for sorting of Route");
        return param;
    }
}
