package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RouteServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static JourneyService journeyService;
    static StopService stopService;

    static StationEntity odessa;
    static StationEntity kyiv;
    static StationEntity gmerinka;
    static StationEntity vapnyarka;
    static StationEntity fastiv;
    static StationEntity lviv;
    static StationEntity dnepr;
    static StationEntity kharkiv;
    static StationEntity khmelnytskyi;
    static StationEntity ternopil;
    static StationEntity chernihiv;
    static StationEntity voznesensk;
    RouteEntity routeEntity;
    StopEntity stopEntity;


    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        vehicleService = applicationContext.getBean(VehicleService.class);
        stationService = applicationContext.getBean(org.hillel.service.StationService.class);
        routeService = applicationContext.getBean(RouteService.class);
        stopService = applicationContext.getBean(StopService.class);

        odessa = stationService.findOneByName("Odessa");
        kyiv = stationService.findOneByName("Kyiv");
        gmerinka = stationService.findOneByName("Zhmerynka");
        vapnyarka = stationService.findOneByName("Vapniarka");
        fastiv = stationService.findOneByName("Fastiv");
        lviv = stationService.findOneByName("Lviv");
        dnepr = stationService.findOneByName("Dnipro");
        kharkiv = stationService.findOneByName("Kharkiv");
        khmelnytskyi = stationService.findOneByName("Khmelnytskyi");
        ternopil = stationService.findOneByName("Ternopil");
        chernihiv = stationService.findOneByName("Chernihiv");
        voznesensk = stationService.findOneByName("Voznesensk");

    }

    @Test
    void routeService() {

        StationEntity odessa = stationService.findOneByName("Odessa");
        StationEntity kyiv = stationService.findOneByName("Kyiv");
        StationEntity gmerinka = stationService.findOneByName("Zhmerynka");
        StationEntity vapnyarka = stationService.findOneByName("Vapniarka");
        StationEntity fastiv = stationService.findOneByName("Fastiv");
        StationEntity lviv = stationService.findOneByName("Lviv");
        StationEntity dnepr = stationService.findOneByName("Dnipro");
        StationEntity kharkiv = stationService.findOneByName("Kharkiv");
        StationEntity khmelnytskyi = stationService.findOneByName("Khmelnytskyi");
        StationEntity ternopil = stationService.findOneByName("Ternopil");
        StationEntity chernihiv = stationService.findOneByName("Chernihiv");
        RouteEntity routeEntity = null;

//        LocalTime time = LocalTime.of(21,27);
//        routeEntity = new RouteEntity("008Ш", odessa, kharkiv, time, (long) (11 * 3600 + 8 * 60));
//        routeService.save(routeEntity);

//        routeEntity = new RouteEntity("012Ш", odessa, lviv, LocalTime.of(22,5), (long) (9 * 3600 + 50 * 60));
//        routeService.save(routeEntity);
//        routeEntity = new RouteEntity("012Л", lviv, odessa, LocalTime.of(22,15), (long) (10 * 3600 + 28 * 60));
//        routeService.save(routeEntity);


//        routeEntity = new RouteEntity("106Ш", odessa, kyiv, LocalTime.of(22,25), (long) (8 * 3600 + 45 * 60));
//        routeService.save(routeEntity);
//        routeEntity = new RouteEntity("105К", kyiv, odessa, LocalTime.of(21,15), (long) (9 * 3600 + 3 * 60));
//        routeService.save(routeEntity);

//        routeEntity = new RouteEntity("764О", odessa, kyiv, LocalTime.of(5,42), (long) (7 * 3600 + 1*60));
//        routeService.save(routeEntity);
//        routeEntity = new RouteEntity("763К", kyiv, odessa, LocalTime.of(16,35), (long) (7 * 3600 + 11 * 60));
//        routeService.save(routeEntity);

//        routeEntity = routeService.findOneByName("32");
//        routeEntity.addStation(vapnyarka);
//        routeService.save(routeEntity);

        routeEntity = new RouteEntity("054Л", odessa, dnepr, LocalTime.of(20,20), (long) (12 * 3600 + 27*60));
        routeService.save(routeEntity);
        routeEntity = new RouteEntity("053П", dnepr, odessa, LocalTime.of(19,10), (long) (11 * 3600 + 26 * 60));
        routeService.save(routeEntity);
    }

/*    @Test
    void dneprLviv(){
        routeEntity = new RouteEntity("041П", dnepr, lviv, LocalTime.of(14,25), (long) (18 * 3600 + 11*60));
        routeService.save(routeEntity);

        stopEntity = new StopEntity(routeEntity, dnepr, LocalTime.of(14, 25), 0);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, gmerinka, LocalTime.of(2, 23), 5);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, khmelnytskyi, LocalTime.of(4, 12), 10);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, ternopil, LocalTime.of(6, 25), 15);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, lviv, LocalTime.of(8, 36), 0);
        stopService.save(stopEntity);

        routeEntity = new RouteEntity("042Л", lviv, dnepr, LocalTime.of(14,57), (long) (18 * 3600 + 20 * 60));
        routeService.save(routeEntity);

        stopEntity = new StopEntity(routeEntity, lviv, LocalTime.of(14, 57), 0);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, ternopil, LocalTime.of(17, 29), 15);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, khmelnytskyi, LocalTime.of(19, 43), 10);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, gmerinka, LocalTime.of(21, 13), 5);
        stopService.save(stopEntity);
        stopEntity = new StopEntity(routeEntity, dnepr, LocalTime.of(14, 25), 0);
        stopService.save(stopEntity);
    }*/

    @Test
    void dneprKha(){
        routeEntity = new RouteEntity("794К", dnepr, kharkiv, LocalTime.of(18,33), (long) (3 * 3600 + 43*60));
        routeService.save(routeEntity);
        routeEntity = new RouteEntity("795К", dnepr, kharkiv, LocalTime.of(18,31), (long) (3 * 3600 + 39*60));
        routeService.save(routeEntity);
    }

    @Test
    void anull(){
        QueryParam param =null;
        Page<RouteEntity> page;
        page = routeService.getFilteredPaged(param);
        page.forEach(System.out::println);
        assertEquals(8, page.getSize());
    }
}