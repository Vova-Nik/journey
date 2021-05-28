package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.enums.StationType;
import org.hillel.persistence.entity.enums.VehicleType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;

import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static JourneyService journeyService;

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        vehicleService = applicationContext.getBean(VehicleService.class);
        stationService = applicationContext.getBean(org.hillel.service.StationService.class);
        routeService = applicationContext.getBean(RouteService.class);
    }


    @Test
    void addSrarions() {

        StationEntity st = new StationEntity("Ternopil");
        st.setStationType(StationType.TRANSIT);
        st = stationService.save(st);
/*
        StationEntity kyiv = new StationEntity("Kyiv");
        kyiv.setStationType(StationType.TRANSIT);
        kyiv = stationService.save(kyiv);

        StationEntity station = new StationEntity("Gmerinka");
        station.setStationType(StationType.TRANSIT);
        stationService.save(station);

        station = new StationEntity("Vapnyarka");
        station.setStationType(StationType.TRANSIT);
        stationService.save(station);

        station = new StationEntity("Fastov");
        station.setStationType(StationType.TRANSIT);
        stationService.save(station);

        station = new StationEntity("Lviv");
        station.setStationType(StationType.TRANSIT);
        stationService.save(station);

        station = new StationEntity("Dnepr");
        station.setStationType(StationType.TRANSIT);
        stationService.save(station);

        station = new StationEntity("Kharkiv");
        station.setStationType(StationType.TRANSIT);
        stationService.save(station);

        assertEquals(8, stationService.count());
        */

    }

    @Test
    void addRoutesToStations() {
        RouteEntity route = null;
        StationEntity station = null;
/*
        RouteEntity route = new RouteEntity("Odessa", "Kyiv", VehicleType.TRAIN);
        Example<RouteEntity> example = Example.of(route);
        route = routeService.findAllByExample(example).get(0);

        StationEntity station = stationService.findOneByName("Gmerinka");
        stationService.addRoute(station, route);

        station = stationService.findOneByName("Vapnyarka");
        stationService.addRoute(station, route);

        station = stationService.findOneByName("Fastov");
        stationService.addRoute(station, route);

        route = new RouteEntity("Kyiv", "Odessa", VehicleType.TRAIN);
        example = Example.of(route);
        route = routeService.findAllByExample(example).get(0);

        station = stationService.findOneByName("Gmerinka");
        stationService.addRoute(station, route);

        station = stationService.findOneByName("Vapnyarka");
        stationService.addRoute(station, route);

        station = stationService.findOneByName("Fastov");
        stationService.addRoute(station, route);
        */


       station = stationService.findOneByName("Ternopil");

        route = routeService.findById(23L);
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findById(24L);
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findById(31L);
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findById(32L);
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findById(15L);
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findById(16L);
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        System.out.println(stationService.getConnectedRoutes(station.getId()));

        /*
        route = routeService.findOneByName("12");
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findOneByName("21");
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findOneByName("22");
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);

        route = routeService.findOneByName("221");
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);
*/
/*        route = routeService.findOneByName("222");
        stationService.addRoute(station, route);
        routeService.addStation(route.getId(),station);*/


//        route = routeService.findOneByName("222");
        System.out.println(station);
        System.out.println(route);
    }


}