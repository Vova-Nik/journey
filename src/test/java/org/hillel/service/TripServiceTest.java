package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.TripEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.enums.VehicleType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static ClientService clientService;
    static TripService tripService;

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        vehicleService = applicationContext.getBean(VehicleService.class);
        stationService = applicationContext.getBean(org.hillel.service.StationService.class);
        routeService = applicationContext.getBean(RouteService.class);
        clientService = applicationContext.getBean(ClientService.class);
        tripService = applicationContext.getBean(TripService.class);
    }
    @Test
    void initiate() {

    /*    RouteEntity route = new RouteEntity("Odessa", "Kyiv", VehicleType.TRAIN);
        Example<RouteEntity> example = Example.of(route);
        route = routeService.findAllByExample(example).get(0);
        VehicleEntity vehicle = vehicleService.findOneByName("Chernomorec");
        TripEntity trip = new TripEntity(route,vehicle,  LocalDate.now());
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(1));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(2));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(3));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(4));
        tripService.save(trip);

        RouteEntity route = new RouteEntity("Kyiv", "Odessa", VehicleType.TRAIN);
        Example<RouteEntity> example = Example.of(route);
        route = routeService.findAllByExample(example).get(0);
        VehicleEntity vehicle = vehicleService.findOneByName("Chernomorec");
        TripEntity trip = new TripEntity(route,vehicle,  LocalDate.now());
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(1));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(2));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(3));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(4));
        tripService.save(trip);

        RouteEntity route = new RouteEntity("Kyiv", "Lviv", VehicleType.TRAIN);
        Example<RouteEntity> example = Example.of(route);
        route = routeService.findAllByExample(example).get(0);
        VehicleEntity vehicle = vehicleService.findOneByName("Kamenyar");
        TripEntity trip = new TripEntity(route,vehicle,  LocalDate.now());
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(1));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(2));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(3));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(4));
        tripService.save(trip);


        RouteEntity route = new RouteEntity("Lviv", "Kyiv", VehicleType.TRAIN);
        Example<RouteEntity> example = Example.of(route);
        route = routeService.findAllByExample(example).get(0);
        VehicleEntity vehicle = vehicleService.findOneByName("Kamenyar");
        TripEntity trip = new TripEntity(route,vehicle,  LocalDate.now());
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(1));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(2));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(3));
        tripService.save(trip);
        trip = new TripEntity(route,vehicle, LocalDate.now().plusDays(4));
        tripService.save(trip);
         */
    }
}