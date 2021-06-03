package org.hillel.service;

import org.hillel.dto.dto.QueryParam;
import org.hillel.exceptions.UnableToRemove;
import org.hillel.persistence.entity.ClientEntity;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.TripEntity;
import org.hillel.persistence.jpa.repository.RouteJPARepository;
import org.hillel.persistence.jpa.repository.StationJPARepository;
import org.hillel.persistence.jpa.repository.specification.ClientSpecification;
import org.hillel.persistence.jpa.repository.specification.StationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Service("StationService")
public class StationService extends EntityServiceImplementation<StationEntity, Long> {

    private final StationJPARepository stationRepository;
    @Autowired
    private RouteJPARepository routeRepository;

    @Autowired
    public StationService(StationJPARepository stationRepository) {
        super(StationEntity.class, stationRepository);
        this.stationRepository = stationRepository;
    }

    @Transactional(readOnly = true)
    public boolean containsRoute(final StationEntity station, final Long routeId) {
        if (Objects.isNull(station) || Objects.isNull(station.getId()) || Objects.isNull(routeId))
            throw new IllegalArgumentException("RouteService.containsStation bad input");
        StationEntity st = stationRepository.findById(station.getId()).orElseThrow(() -> new IllegalArgumentException("RouteService.containsStation can not find route"));
        return st.containsRoute(routeId);
    }

    @Transactional
    public void addRoute(final StationEntity station, final RouteEntity route) {
        StationEntity st = findById(station.getId());
        st.addRoute(route);
    }

    @Transactional
    public void removeRoute(final StationEntity station, final RouteEntity route) {
        StationEntity st = findById(station.getId());
        st.removeRoute(route);
    }

    @Transactional(readOnly = true)
    public Set<Long> getConnectedRoutesIds(Long id){
        StationEntity st = stationRepository.findById(id).orElseThrow(()->new IllegalArgumentException("StationService.getConnectedRoutesIds bad Id"));
        return st.getConnectedRoutesIds();
    }

    @Transactional(readOnly = true)
    public Set<Long> getConnectedRoutesIds(String name){
        StationEntity st = stationRepository.findByName(name).get(0);
        return st.getConnectedRoutesIds();
    }

    @Transactional(readOnly = true)
    public Set<RouteEntity> getConnectedRoutes(Long id){
        StationEntity st = stationRepository.findById(id).orElseThrow(()->new IllegalArgumentException("StationService.getConnectedRoutes bad Id"));
        Set<RouteEntity> routes = new HashSet<>();
        Set<Long> routeIds =  st.getConnectedRoutesIds();
        routeIds.forEach(routeId-> routes.add(routeRepository.findById(routeId).orElseThrow(()->new IllegalArgumentException("StationService.getConnectedRoutes bad Id"))));
        return routes;
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws UnableToRemove {
        StationEntity station = findById(id);
        Set<Long> routeIds = getConnectedRoutesIds(id);
        for (Long routeId : routeIds) {
            RouteEntity route = routeRepository.findById(routeId).orElseThrow(() -> new UnableToRemove("StationService.deleteById"));
            route.removeStation(station);
        }
            stationRepository.deleteById(id);
    }

    @Override
    boolean isValid(StationEntity entity) {
        return entity.isValid();
    }

    @Transactional(readOnly = true)
    public List<StationEntity> getFiltered(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        checkInput(param);
        return stationRepository.findAll(StationSpecification.byQueryParam(param));
    }

    @Transactional(readOnly = true)
    public Page<StationEntity> getFilteredPaged(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        param = checkInput(param);
        PageRequest page = PageRequest.of(param.getPageNumber(), param.getPageSize());
        return stationRepository.findAll(StationSpecification.byQueryParam(param), page);
    }

    private QueryParam checkInput(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        Field[] allFields = StationEntity.class.getDeclaredFields();
        String column = param.getSortColumn();
        boolean checked = Arrays.stream(allFields).anyMatch(field ->
                column.equals(field.getName()));
        if (column.equals("id"))
            checked = true;
        if (!checked)
            throw new IllegalArgumentException("Insufficient column name = \""+ column + "\" for sorting of Station");
        return param;
    }

}
