package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StationJPARepository extends CommonRepository<StationEntity,Long>, JpaSpecificationExecutor<StationEntity>{

    @Query("select s from StationEntity s where s.name = :stationFrom or s.name = :stationTo")
    List<StationEntity> findPairByName(@Param("stationFrom") String stationFrom, @Param("stationTo") String  stationTo);
}
