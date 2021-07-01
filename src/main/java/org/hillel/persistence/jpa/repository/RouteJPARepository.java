package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RouteJPARepository extends CommonRepository<RouteEntity,Long>, JpaSpecificationExecutor<RouteEntity> {

//    @Query("select r from RouteEntity r inner join RouteEntity r on r.stations = stationFromId where r.id=:routeId and t.departureDate=:date")
//    List<RouteEntity> findByStations(@Param("stationFrom") Long stationFromId, @Param("stationTo") Long stationToId);

}

