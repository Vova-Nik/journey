package org.hillel.service;

import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.ClientEntity;
import org.hillel.persistence.entity.TripEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.jpa.repository.VehicleJPARepository;
import org.hillel.persistence.jpa.repository.specification.ClientSpecification;
import org.hillel.persistence.jpa.repository.specification.VehicleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Service(value = "vehicleService")

public class VehicleService extends EntityServiceImplementation<VehicleEntity, Long>{

    private final VehicleJPARepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleJPARepository vehicleRepository){
        super(VehicleEntity.class, vehicleRepository);
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    boolean isValid(VehicleEntity vehicle) {
        return vehicle.isValid();
    }

    @Transactional
    public List<VehicleEntity> getByNameActiveSpecification(final String name) {
        if (Objects.isNull(name))
            throw new IllegalArgumentException("VehicleEntity.getByNameActiveSpecification insufficient name parameter");
        return vehicleRepository.findAll(VehicleSpecification.byName(name).and(VehicleSpecification.onlyActive()));
    }

    @Transactional
    public List<VehicleEntity> getByNameOrderedSpecification(final String sortBy) {
        if (Objects.isNull(sortBy))
            throw new IllegalArgumentException("VehicleEntity.getByNameOrdered insufficient name string");
        if(!checkIfColumnExists(sortBy))
            throw new IllegalArgumentException("VehicleEntity.getByNameOrdered There is no" + sortBy + " fild in VehicleEntity");
        return vehicleRepository.findAll(VehicleSpecification.ordered(sortBy));
    }

    @Transactional(readOnly = true)
    public List<VehicleEntity> getFiltered(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        checkInput(param);
        return vehicleRepository.findAll(VehicleSpecification.byQueryParam(param));
    }

    @Transactional(readOnly = true)
    public Page<VehicleEntity> getFilteredPaged(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        param = checkInput(param);
        PageRequest page = PageRequest.of(param.getPageNumber(), param.getPageSize());
        return vehicleRepository.findAll(VehicleSpecification.byQueryParam(param), page);
    }

    private QueryParam checkInput(QueryParam param) {
        if (Objects.isNull(param))
            param = new QueryParam();
        Field[] allFields = VehicleEntity.class.getDeclaredFields();
        String column = param.getSortColumn();
        boolean checked = Arrays.stream(allFields).anyMatch(field ->
                column.equals(field.getName()));
        if (column.equals("id"))
            checked = true;
        if (!checked)
            throw new IllegalArgumentException("Insufficient column name = \""+ column + "\" for sorting of Vehicle");
        return param;
    }

}
