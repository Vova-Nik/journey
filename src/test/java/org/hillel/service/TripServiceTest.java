package org.hillel.service;

import org.hibernate.exception.ConstraintViolationException;
import org.hillel.config.RootConfig;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.TripEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.enums.VehicleType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static UserService clientService;
    static TripService tripService;
    RouteEntity route;
    VehicleEntity vehicle;
    TripEntity trip;
    Example<RouteEntity> example;

    static class ProtoTrip {
        private String routeName;
        private String vehicleName;

        public ProtoTrip(String routeName, String vehicleName) {
            this.routeName = routeName;
            this.vehicleName = vehicleName;
        }

        public String getRouteName() {
            return routeName;
        }

        public String getVehicleName() {
            return vehicleName;
        }
    }

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        vehicleService = applicationContext.getBean(VehicleService.class);
        stationService = applicationContext.getBean(org.hillel.service.StationService.class);
        routeService = applicationContext.getBean(RouteService.class);
        clientService = applicationContext.getBean(UserService.class);
        tripService = applicationContext.getBean(TripService.class);
    }

    @Test
    void initiateWeek() {

        Set<ProtoTrip> protos = new HashSet<>();
        protos.add(new ProtoTrip("10", "Kamenyar"));
        protos.add(new ProtoTrip("9", "Kamenyar"));
        protos.add(new ProtoTrip("11", "Oriental Express"));
        protos.add(new ProtoTrip("12", "Oriental Express"));
        protos.add(new ProtoTrip("21", "Oriental Express"));
        protos.add(new ProtoTrip("22", "Oriental Express"));
        protos.add(new ProtoTrip("31", "Chernomorec"));
        protos.add(new ProtoTrip("32", "Chernomorec"));
        protos.add(new ProtoTrip("41", "Kamenyar"));
        protos.add(new ProtoTrip("42", "Kamenyar"));
        protos.add(new ProtoTrip("51", "Oriental Express"));
        protos.add(new ProtoTrip("52", "Oriental Express"));
        protos.add(new ProtoTrip("128", "Oriental Express"));
        protos.add(new ProtoTrip("129", "Oriental Express"));
        protos.add(new ProtoTrip("210", "Oriental Express"));
        protos.add(new ProtoTrip("211", "Oriental Express"));
        protos.add(new ProtoTrip("221", "Oriental Express"));
        protos.add(new ProtoTrip("222", "Oriental Express"));
        int ofset = 7;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //formatter = formatter.withLocale( putAppropriateLocaleHere );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse("2021-07-19", formatter);
        int num = 7;
        System.out.println(date);

        protos.forEach(proto -> {
            route = routeService.findOneByName(proto.getRouteName());
            vehicle = vehicleService.findOneByName(proto.getVehicleName());
            for (int i = 0; i < num; i++) {
                trip = new TripEntity(route, vehicle, date.plusDays(i));
                tripService.saveAnyWay(trip);
            }
        });
    }

    @Test
    void getIn() {
        List<Long> ids = new ArrayList<>();
        ids.add(15L);
        ids.add(16L);

        List<TripEntity> trips = tripService.getIn(ids);
        System.out.println("Trips Size = " + trips.size());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2021-07-05", formatter);
        List<TripEntity> tripsD = tripService.getByRouteAndDate(ids, date);
        System.out.println("Trips Size = " + tripsD.size());
    }

    /*
            route = new RouteEntity("Odessa", "Kyiv", VehicleType.TRAIN);
            example = Example.of(route);
            route = routeService.findAllByExample(example).get(0);
            vehicle = vehicleService.findOneByName("Chernomorec");
            trip = new TripEntity(route, vehicle, LocalDate.now());
            tripService.save(trip);
            trip = new TripEntity(route, vehicle, LocalDate.now().plusDays(1));
            tripService.save(trip);
            trip = new TripEntity(route, vehicle, LocalDate.now().plusDays(2));
            tripService.save(trip);
            trip = new TripEntity(route, vehicle, LocalDate.now().plusDays(3));
            tripService.save(trip);
            trip = new TripEntity(route, vehicle, LocalDate.now().plusDays(4));
            tripService.save(trip);
    */

    @Test
    void getTripService() {
        List<TripEntity> trips = tripService.getByrootSpec(3l);
        System.out.println(trips);
    }

    @Test
    void complex() {
        QueryParam param = new QueryParam();
        //        param.setFilterValue(" ");
        //        param.setFilterKey("name");
        //        param.setFilterOperation("more");
        param.setSortColumn("name");

        //        List<ClientEntity> page = clientService.getComplex(param);
        //        System.out.println(page);
        Page<TripEntity> page;

        param.setPageSize(8);
        param.setPageNumber(0);
        page = tripService.getFilteredPaged(param);
        page.forEach(System.out::println);

        param.setPageSize(8);
        param.setPageNumber(1);
        page = tripService.getFilteredPaged(param);
        page.forEach(System.out::println);

        param.setPageSize(8);
        param.setPageNumber(2);
        page = tripService.getFilteredPaged(param);
        page.forEach(System.out::println);
    }

    @Test
    void anull() {
        QueryParam param = null;
        Page<TripEntity> page;
        page = tripService.getFilteredPaged(param);
        page.forEach(System.out::println);
        assertEquals(8, page.getSize());
    }

    @Test
    void bigSell() {
        int num;
        List<TripEntity> trips = tripService.findAll();
        Instant today = Instant.now();
        for (TripEntity trip : trips) {
            if (today.isBefore(trip.getDeparture())) {
                num = (int) (Math.random() * (100 + 1));
                trip.sellTicket(num);
                tripService.save(trip);
            }
        }
    }

}