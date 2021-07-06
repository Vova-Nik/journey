package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StopJPARepository extends CommonRepository<StopEntity,Long> {

    StopEntity findOneByName(String name);

    @Query("select s.route from StopEntity s join StopEntity ss on s.route = ss.route where s.name = :stationFrom and ss.name = :stationTo")
    List<Long> findRoute(@Param("stationFrom") String stationFrom, @Param("stationTo") String  stationTo);

    @Query("select s from StopEntity s where s.route = :route")
    Set<StopEntity> findAllByRoute(@Param("route") RouteEntity route);

    @Query("select s from StopEntity s where s.station = :station")
    Set<StopEntity> findStopsByStation(@Param("station") StationEntity station);

}
