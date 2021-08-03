package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.SynonimEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynonimJPARepozitory extends CommonRepository<SynonimEntity,Long>{

    @Query("select s from SynonimEntity s where s.trueName = :stationName")
    List<SynonimEntity> findSynonimsByStationName(@Param("stationName") String stationName);
}
