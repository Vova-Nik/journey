package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.dto.dto.QueryParam;
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
import org.springframework.data.domain.Page;

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
    void addStations() {

        StationEntity st = new StationEntity("Voznesensk");
        st.setStationType(StationType.TRANSIT);
        st.setDescription(
                "Voznesensk nice city situated on banks Yuzniy Buh river. Railway station is an important stop along the Odessa railroad, with direct trains available to major cities."
        );
        st = stationService.save(st);

//        st = new StationEntity("Odessa");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "Odessa is a port city on the Black Sea in southern Ukraine. It’s known for its beaches and 19th-century architecture, including the Odessa Opera and Ballet Theater. "
//        );
//        st = stationService.save(st);
//
//        st = new StationEntity("Lviv");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "Lviv is a city in western Ukraine, around 70 kilometers from the border with Poland. Traces of its Polish and Austro-Hungarian heritage are evident in its architecture,"
//        );
//        st = stationService.save(st);
//
//        st = new StationEntity("Kyiv");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "Kyiv or Kiev is the capital and most populous city of Ukraine. It is in north-central Ukraine along the Dnieper River. "
//        );
//        st = stationService.save(st);
//
//         st = new StationEntity("Kharkiv");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "Kharkiv is a city in northeast Ukraine. Sprawling Freedom Square is home to the constructivist Derzhprom building."
//        );
//        st = stationService.save(st);
//
//         st = new StationEntity("Dnipro");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "Dnipro is a city on the Dnieper River in central Ukraine. Missiles in Rocket Park mark the city's role in the Soviet-era space and defense industries."
//        );
//        st = stationService.save(st);
//
//         st = new StationEntity("Zhmerynka");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "There are many propositions as far as the origin of name of Zhmerynka. One of the ideas is that it may be derived from the Polish words, describing the handshake."
//        );
//        st = stationService.save(st);
//
//         st = new StationEntity("Vapniarka");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "is an urban-type settlement in Vinnytsia Oblast, Ukraine, known since 1870 as a railroad station. Its name from the Ukrainian language translates as a lime settlement."
//        );
//        st = stationService.save(st);
//
//         st = new StationEntity("Khmelnytskyi");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "Khmelnytskyi is located in the historic region of Podolia on the banks of the Buh River. The city received its current local government designation in 1941."
//        );
//        st = stationService.save(st);
//
//         st = new StationEntity("Chernihiv");
//        st.setStationType(StationType.TRANSIT);
//        st.setDescription(
//                "Chernihiv is a city in northern Ukraine. In its historic center, Dytynets Park is home to churches like the 11th-century Transfiguration Cathedral."
//        );
//        st = stationService.save(st);
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


       station = stationService.findOneByName("Kharkiv");



//        route = routeService.findById(27L);
//        stationService.addRoute(station, route);
//        routeService.addStation(route.getId(),station);
//
//        route = routeService.findById(28L);
//        stationService.addRoute(station, route);
//        routeService.addStation(route.getId(),station);
//
//        route = routeService.findById(31L);
//        stationService.addRoute(station, route);
//        routeService.addStation(route.getId(),station);
//
//        route = routeService.findById(32L);
//        stationService.addRoute(station, route);
//        routeService.addStation(route.getId(),station);


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

    @Test
    void anull(){
        QueryParam param =null;
        Page<StationEntity> page;
        page = stationService.getFilteredPaged(param);
        page.forEach(System.out::println);
        assertEquals(8, page.getSize());
    }

}