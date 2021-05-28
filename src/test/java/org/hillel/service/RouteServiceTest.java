package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.enums.StationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class RouteServiceTest {

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
    void routeService() {

        StationEntity odessa = stationService.findOneByName("Odessa");
        StationEntity kyiv = stationService.findOneByName("Kyiv");
        StationEntity gmerinka = stationService.findOneByName("Gmerinka");
        StationEntity vapnyarka = stationService.findOneByName("Vapnyarka");
        StationEntity fastov = stationService.findOneByName("Fastov");
        StationEntity lviv = stationService.findOneByName("Lviv");
        StationEntity dnepr = stationService.findOneByName("Dnepr");
        StationEntity kharkiv = stationService.findOneByName("Kharkiv");
        StationEntity ternopil = stationService.findOneByName("Ternopil");
        RouteEntity routeEntity = null;

        routeEntity = routeService.findById(31L);
        routeEntity.addStation(ternopil);

        routeEntity = routeService.findById(32L);
        routeEntity.addStation(ternopil);





/*
        RouteEntity routeEntity1 = new RouteEntity("10", lviv, odessa, new Time(18, 20, 0), 25000L);
        routeEntity1 = routeService.save(routeEntity1);
        RouteEntity routeEntity2 = new RouteEntity("9", odessa, lviv, new Time(19, 20, 0), 24500L);
        routeEntity2 = routeService.save(routeEntity2);

         routeEntity1 = new RouteEntity("11", lviv, dnepr, new Time(21, 35, 0), 25000L);
        routeEntity1 = routeService.save(routeEntity1);
         routeEntity2 = new RouteEntity("12", dnepr, lviv, new Time(22, 15, 0), 24500L);
        routeEntity2 = routeService.save(routeEntity2);

        routeEntity1 = new RouteEntity("21", odessa, dnepr, new Time(21, 05, 0), 19000L);
        routeEntity1 = routeService.save(routeEntity1);
        routeEntity2 = new RouteEntity("22", dnepr, odessa, new Time(22, 55, 0), 24500L);
        routeEntity2 = routeService.save(routeEntity2);

        RouteEntity routeEntity11 = new RouteEntity("31", odessa, kyiv, new Time(20, 0, 0), 31000L);
        routeEntity11 = routeService.save(routeEntity11);
        RouteEntity routeEntity12 = new RouteEntity("32", kyiv, odessa, new Time(19, 6, 0), 30500L);
        routeEntity12 = routeService.save(routeEntity12);
        routeEntity11.addStation(stationService.findOneByName("Gmerinka"));


         routeEntity1 = new RouteEntity("41", lviv, kyiv, new Time(18, 45, 0), 23000L);
        routeEntity1 = routeService.save(routeEntity1);
         routeEntity2 = new RouteEntity("42", kyiv, lviv, new Time(19, 20, 0), 22500L);
        routeEntity2 = routeService.save(routeEntity2);

        routeEntity1 = new RouteEntity("51", dnepr, kyiv, new Time(21, 45, 0), 19000L);
        routeEntity1 = routeService.save(routeEntity1);
        routeEntity2 = new RouteEntity("52", kyiv, dnepr, new Time(20, 20, 0), 18500L);
        routeEntity2 = routeService.save(routeEntity2);

        routeEntity1 = new RouteEntity("128", kharkiv, kyiv, new Time(11, 45, 0), 13000L);
        routeEntity1 = routeService.save(routeEntity1);
        routeEntity2 = new RouteEntity("129", kyiv, kharkiv, new Time(11, 20, 0), 12500L);
        routeEntity2 = routeService.save(routeEntity2);


        RouteEntity routeEntity1 = new RouteEntity("210", dnepr, kharkiv, new Time(11, 20, 0), 5000L);
        routeEntity1 = routeService.save(routeEntity1);
        RouteEntity routeEntity2 = new RouteEntity("211", kharkiv, dnepr, new Time(10, 20, 0), 4500L);
        routeEntity2 = routeService.save(routeEntity2);

        routeEntity1 = new RouteEntity("221", lviv, kharkiv, new Time(21, 40, 0), 35000L);
        routeEntity1 = routeService.save(routeEntity1);
        routeEntity2 = new RouteEntity("222", kharkiv, lviv, new Time(22, 35, 0), 34500L);
        routeEntity2 = routeService.save(routeEntity2);
   */
//        routeService.deleteAll();
    }
}