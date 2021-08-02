package org.hillel.service;

import org.hillel.dto.dto.QueryParam;
import org.hillel.exceptions.UnableToRemove;
import org.hillel.persistence.entity.UserEntity;
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

    @Override
    @Transactional
    public void deleteById(Long id) throws UnableToRemove {
        StationEntity station = findById(id);
        stationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getPairByNames(final String stationFrom, final String stationTo) {
        if (Objects.isNull(stationFrom) || Objects.isNull(stationTo))
            throw new IllegalArgumentException("StationService.getPairByNames not valid args");
        List<StationEntity> stationList = stationRepository.findPairByName(stationFrom, stationTo);
        StationEntity station = stationList.get(0);
        Map<String, Long> result = new HashMap<>();
        if(station.getName().equals(stationFrom)){
            result.put("stationFrom",station.getId());
        }else{
            result.put("stationTo",station.getId());
        }
        station = stationList.get(1);
        if(station.getName().equals(stationFrom)){
            result.put("stationFrom",station.getId());
        }else{
            result.put("stationTo",station.getId());
        }
        if(result.size()<2)  throw new IllegalArgumentException("StationService.getPairByNames unable to find both stations");
        return result;
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
            throw new IllegalArgumentException("Insufficient column name = \"" + column + "\" for sorting of Station");
        return param;
    }

}
