package org.hillel.service;

import org.hibernate.exception.ConstraintViolationException;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.*;
import org.hillel.persistence.jpa.repository.TripJPARepository;
import org.hillel.persistence.jpa.repository.specification.ClientSpecification;
import org.hillel.persistence.jpa.repository.specification.TripSpecification;
import org.hillel.persistence.jpa.repository.specification.VehicleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service("TripService")
public class TripService extends EntityServiceImplementation<TripEntity, Long> {

    private final TripJPARepository tripRepository;

    @Autowired
    public TripService(TripJPARepository repository) {
        super(TripEntity.class, repository);
        this.tripRepository = repository;
    }

    @Transactional
    public TripEntity save(TripEntity trip) {
        if (Objects.isNull(trip) || !trip.isValid())
            throw new IllegalArgumentException("TripService TripEntity.create is not valid");
        TripEntity result;
        try {
            result = tripRepository.save(trip);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("TripService.save such trip already exists");
        }
        return result;
    }

    @Transactional
    public TripEntity saveAnyWay(TripEntity trip) {
        TripEntity result =  null;
        if (Objects.isNull(trip) || !trip.isValid())
            throw new IllegalArgumentException("TripService TripEntity.create is not valid");
        try {
            result = tripRepository.save(trip);
        } catch (DataIntegrityViolationException | ConstraintViolationException ignored){

        }
        return result;
    }


    @Transactional
    public boolean sellTicket(Long id) {
        TripEntity trip = findById(id);
        if (trip.sellTicket()) {
            save(trip);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public int getAllPlacesAmmount(Long id) {
        TripEntity trip = findById(id);
        return trip.getAvailible();
    }

    @Transactional(readOnly = true)
    public int getFreePlaces(Long id) {
        TripEntity trip = findById(id);
        return trip.getAvailible();
    }

    @Transactional(readOnly = true)
    public List<TripEntity> findByRouteAndDate(final Long routeId, final LocalDate departure) {
        return tripRepository.findByRouteAndDate(routeId, departure);
    }

    @Transactional(readOnly = true)
    public List<TripEntity> findByRouteAndDateActive(final Long routeId, final LocalDate departure) {
        return tripRepository.findByRouteAndDateActive(routeId, departure);
    }

    @Override
    boolean isValid(TripEntity entity) {
        return entity.isValid();
    }

    @Transactional
    public List<TripEntity> getByrootSpec(final Long rootId) {
        if (Objects.isNull(rootId))
            throw new IllegalArgumentException("TripEntity.getByroot insufficient rootId parameter");
        return tripRepository.findAll(TripSpecification.findByRoute(rootId));
    }

    @Transactional
    public List<TripEntity> getByDateSpec(final LocalDate date) {
        if (Objects.isNull(date))
            throw new IllegalArgumentException("TripEntity.getByroot insufficient rootId parameter");
        return tripRepository.findAll(TripSpecification.findByDate(date));
    }

    @Transactional
    public List<TripEntity> getByRootDateSpec(final Long rootId, final LocalDate date) {
        if (Objects.isNull(rootId) || Objects.isNull(date))
            throw new IllegalArgumentException("TripEntity.getByroot insufficient rootId parameter");
        return tripRepository.findAll(TripSpecification.findByRoute(rootId).and(TripSpecification.findByDate(date)));
    }

    @Transactional(readOnly = true)
    public List<TripEntity> getFiltered(QueryParam param) {
        param = checkInput(param);
        return tripRepository.findAll(TripSpecification.filtered(param));
    }

    @Transactional(readOnly = true)
    public List<TripEntity> getIn(List<Long> routeIds) {
        return tripRepository.findAll(TripSpecification.routesIn(routeIds));
    }

    @Transactional(readOnly = true)
    public List<TripEntity> getByRouteAndDate(final List<Long> routeIds, final LocalDate date) {
        return tripRepository.findAll(TripSpecification.routesIn(routeIds).and(TripSpecification.findByDate(date)));
    }

    @Transactional(readOnly = true)
    public Page<TripEntity> getFilteredPaged(QueryParam param) {
        param = checkInput(param);
        PageRequest page = PageRequest.of(param.getPageNumber(), param.getPageSize());
        return tripRepository.findAll(TripSpecification.filtered(param), page);
    }

    private QueryParam checkInput(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        Field[] allFields = TripEntity.class.getDeclaredFields();
        String column = param.getSortColumn();
        boolean checked = Arrays.stream(allFields).anyMatch(field ->
                column.equals(field.getName()));
        if (column.equals("id"))
            checked = true;
        if (!checked)
            throw new IllegalArgumentException("Insufficient column name = \"" + column + "\" for sorting of Trip");
        return param;
    }
}
