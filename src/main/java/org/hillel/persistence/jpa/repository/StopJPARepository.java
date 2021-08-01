package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface StopJPARepository extends CommonRepository<StopEntity,Long> {

    StopEntity findOneByName(String name);

//    @Query("select s.route from StopEntity s join StopEntity ss on s.route = ss.route where s.name = :stationFrom and ss.name = :stationTo and s.secOffset < ss.secOffset")
//    List<RouteEntity> findRoutesByJourney(@Param("stationFrom") String stationFrom, @Param("stationTo") String  stationTo);

    @Query("select s from StopEntity s join StopEntity ss on s.route = ss.route where s.name = :stationFrom and ss.name = :stationTo and s.secOffset < ss.secOffset")
    List<StopEntity> findStopsFromByJourney(@Param("stationFrom") String stationFrom, @Param("stationTo") String  stationTo);

    @Query("select s from StopEntity s where s.route = :route")
    Set<StopEntity> findAllByRoute(@Param("route") RouteEntity route);

    @Query("select s from StopEntity s where s.route = :route and s.station = :station")
    List<StopEntity> findByRouteAndStation(@Param("route") RouteEntity route, @Param("station") StationEntity station);

    @Query("select s from StopEntity s where s.station = :station")
    Set<StopEntity> findStopsByStation(@Param("station") StationEntity station);

    @Query("update StopEntity set active = :active, arrival = :arrival, departure=:departure, description=:description, staying=:staying, name=:name, route_id=:route_id, station_id = :station_id, dayoffset = :dayoffset, secoffset=:secoffset where id = :id")
    Set<StopEntity> update(
            @Param("id") long id,

            @Param("active") boolean active,
            @Param("arrival") LocalTime arrival,
            @Param("departure") LocalTime departure,
            @Param("description") String description,
            @Param("staying") int staying,
            @Param("name") String name,
            @Param("route_id") long route_id,
            @Param("station_id") long station_id,
            @Param("dayoffset") int dayoffset,
            @Param("secoffset") int secoffset
    );


}
