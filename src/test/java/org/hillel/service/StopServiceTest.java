package org.hillel.service;

import lombok.ToString;
import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class StopServiceTest {

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
    static RouteEntity route;
    static StopEntity stop;

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
    void create794() {
        route = routeService.findOneByName("794К");
        stop = new StopEntity(route, dnepr, LocalTime.of(18, 33), 0,0);
        stopService.save(stop);
        stop = new StopEntity(route, kharkiv, LocalTime.of(22, 16), 0);
        stopService.save(stop);

        RouteEntity route = routeService.findOneByName("795К");
        stop = new StopEntity(route, kharkiv, LocalTime.of(18, 31), 0);
        stopService.save(stop);
        stop = new StopEntity(route, dnepr, LocalTime.of(22, 10), 0);
        stopService.save(stop);
    }

    @Test
    void sorting() {
        RouteEntity route = routeService.findOneByName("041П");
        System.out.println(route);
        Set<StopEntity> notSorted = stopService.findAllByRoute(route);
        notSorted.forEach(stop-> System.out.println(stop.getName() + " :  " + stop.getArrival()));
        List<StopEntity> sorted = stopService.findAllByRouteSorted(route);
        sorted.forEach(stop-> System.out.println(stop.getName() + " :  " + stop.getArrival()));
    }

    @Test
    void routesOnStation() {
        Set<RouteEntity> routes = stopService.findAllRoutesByStation(gmerinka);
        System.out.println(routes);
    }


    @Test
    void create() {

/*        RouteEntity route = routeService.findOneByName("007Ш");//kha - ode
        StopEntity stop = new StopEntity(route, kharkiv, LocalTime.of(21,36), 0);
        stopService.save(stop);
        stop = new StopEntity(route, odessa, LocalTime.of(9,21), 0);
        stopService.save(stop);

        route = routeService.findOneByName("008Ш");// Ods-kha
        stop = new StopEntity(route, odessa, LocalTime.of(21,27), 0);
        stopService.save(stop);
        stop = new StopEntity(route, kharkiv, LocalTime.of(8,35), 0);
        stopService.save(stop);*/

/*        RouteEntity route = routeService.findOneByName("012Л");
        StopEntity stop = new StopEntity(route, gmerinka, LocalTime.of(3,40), 20*60);
        stopService.save(stop);
        route = routeService.findOneByName("012Ш");
        stop = new StopEntity(route, gmerinka, LocalTime.of(2,37), 20*60);
        stopService.save(stop);

        RouteEntity route = routeService.findOneByName("012Л");//lvv - ods
        StopEntity stop = new StopEntity(route, lviv, LocalTime.of(22,29), 0);
        stopService.save(stop);
         stop = new StopEntity(route, odessa, LocalTime.of(9,2), 0);
        stopService.save(stop);

        route = routeService.findOneByName("012Ш"); //ods - lvv
        stop = new StopEntity(route, odessa, LocalTime.of(21,36), 0);
        stopService.save(stop);
        stop = new StopEntity(route, lviv, LocalTime.of(8,4), 0);
        stopService.save(stop);*/

/*        route = routeService.findOneByName("012Л");
         stop = new StopEntity(route, ternopil, LocalTime.of(0,16), 4*60);
        stopService.save(stop);
        route = routeService.findOneByName("012Ш");
         stop = new StopEntity(route, ternopil, LocalTime.of(6,3), 3*60);
        stopService.save(stop);

         route = routeService.findOneByName("012Л");
         stop = new StopEntity(route, khmelnytskyi, LocalTime.of(2,10), 4*60);
        stopService.save(stop);
        route = routeService.findOneByName("012Ш");
         stop = new StopEntity(route, khmelnytskyi, LocalTime.of(4,20), 3*60);
        stopService.save(stop);


        RouteEntity route = routeService.findOneByName("106Ш");
        StopEntity stop = new StopEntity(route, gmerinka, LocalTime.of(2,10), 4*60);
        stopService.save(stop);
        route = routeService.findOneByName("105К");
        stop = new StopEntity(route, gmerinka, LocalTime.of(4,20), 3*60);


        //"105К", kyiv, odessa,
         route = routeService.findOneByName("105К");
         stop = new StopEntity(route, kyiv, LocalTime.of(21, 15), 0);
        stopService.save(stop);
        stop = new StopEntity(route, odessa, LocalTime.of(6, 18), 0);
        stopService.save(stop);

        //"106Ш", kyiv, odessa,
         route = routeService.findOneByName("106Ш");
         stop = new StopEntity(route, odessa, LocalTime.of(22, 5), 0);
        stopService.save(stop);
        stop = new StopEntity(route, kyiv, LocalTime.of(7, 10), 0);
        stopService.save(stop);*/

/*        //"763О", odessa,  kyiv,
        route = routeService.findOneByName("763О");
        stop = new StopEntity(route, kyiv, LocalTime.of(16, 35), 0);
        stopService.save(stop);
        stop = new StopEntity(route, gmerinka, LocalTime.of(19, 29), 2);
        stopService.save(stop);
        stop = new StopEntity(route, odessa, LocalTime.of(23, 46), 0);
        stopService.save(stop);

        //"764О", odessa,  kyiv,
        route = routeService.findOneByName("764О");
        stop = new StopEntity(route, odessa, LocalTime.of(5, 42), 0);
        stopService.save(stop);
        stop = new StopEntity(route, gmerinka, LocalTime.of(9, 53), 2);
        stopService.save(stop);
        stop = new StopEntity(route, kyiv, LocalTime.of(12, 43), 0);
        stopService.save(stop);*/


    }
/*    @Test
    void create54(){
        // RouteEntity("054Л", odessa, dnepr, LocalTime.of(20,20), (long) (12 * 3600 + 27*60));
        // RouteEntity("053П", dnepr, odessa, LocalTime.of(19,10), (long) (11 * 3600 + 26 * 60));
        route = routeService.findOneByName("054Л");
        stop = new StopEntity(route, odessa, LocalTime.of(20, 20), 0);
        stopService.save(stop);
        stop = new StopEntity(route, voznesensk, LocalTime.of(23, 34), 5);
        stopService.save(stop);
        stop = new StopEntity(route, dnepr, LocalTime.of(8, 47), 0);
        stopService.save(stop);

        route = routeService.findOneByName("053П");
        stop = new StopEntity(route, dnepr, LocalTime.of(19, 10), 0);
        stopService.save(stop);
        stop = new StopEntity(route, voznesensk, LocalTime.of(3, 31), 5);
        stopService.save(stop);
        stop = new StopEntity(route, odessa, LocalTime.of(6, 36), 0);
        stopService.save(stop);
    }*/

    @Test
    void localDateTime(){

    }

}
