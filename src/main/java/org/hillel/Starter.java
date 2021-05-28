package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.service.JourneyService;
import org.hillel.service.RouteService;
import org.hillel.service.StationService;
import org.hillel.service.VehicleService;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Scanner;

public class Starter {
    public static void main(String[] args) {
         ConfigurableApplicationContext applicationContext;
         Environment env;
         StationService stationService;
         RouteService routeService;
         VehicleService vehicleService;
         JourneyService journeyService;

            applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
            vehicleService = applicationContext.getBean(VehicleService.class);
            stationService = applicationContext.getBean(org.hillel.service.StationService.class);
            routeService = applicationContext.getBean(RouteService.class);

        System.out.println("Waiting");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("made");
    }
}
