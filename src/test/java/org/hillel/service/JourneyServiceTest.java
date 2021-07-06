package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.dto.dto.JourneyDto;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.TripEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.util.List;

public class JourneyServiceTest {
    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static JourneyService journeyService;
    static UserService userService;
    static TripService tripService;

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        vehicleService = applicationContext.getBean(VehicleService.class);
        stationService = applicationContext.getBean(org.hillel.service.StationService.class);
        routeService = applicationContext.getBean(RouteService.class);
        userService = applicationContext.getBean(UserService.class);
        tripService = applicationContext.getBean(TripService.class);
        journeyService = applicationContext.getBean(JourneyService.class);
    }

//    @Test
//    public void create(){
//        TripEntity trip = tripService.findById(27l);
//        StationEntity  stationFrom = stationService.findOneByName("Odessa");
//        StationEntity  stationTo = stationService.findOneByName("Kyiv");
//        JourneyEntity journey = new JourneyEntity(trip, stationFrom, stationTo, trip.getDepartureDate());
//        System.out.println(journey);
//        JourneyEntity jr = journeyService.save(journey);
//    }

    @Test
    public void findRelevant() {
        JourneyDto journeyDto = new JourneyDto();
        journeyDto.setStationFrom("Gmerinka");
        journeyDto.setStationTo("Kyiv");
        journeyDto.setDeparture("2021-07-05");

//        private String stationFrom;
//        private String stationTo;
//        private String departure;

//        JourneyDto journeyDto = JourneyDto.builder()
//                .stationFrom("Odessa")
//                .stationTo("Kyiv")
//                .build();
        List<JourneyDto> result = journeyService.findRelevant(journeyDto);
        System.out.println(result);
    }

}
   //public JourneyEntity(final TripEntity trip, final StationEntity stationFrom, final StationEntity stationTo, final LocalDate date) {
