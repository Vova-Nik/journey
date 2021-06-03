package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.VehicleEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import static org.junit.jupiter.api.Assertions.*;

class VehicleServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        vehicleService = applicationContext.getBean(VehicleService.class);
        stationService = applicationContext.getBean(org.hillel.service.StationService.class);
        routeService = applicationContext.getBean(RouteService.class);
        clientService = applicationContext.getBean(ClientService.class);
    }

    @Test
    void initVehicles() {
        /*
        VehicleEntity vehicleEntity = new VehicleEntity("Neoplan", VehicleType.BUS);
        vehicleService.save(vehicleEntity);
         vehicleEntity = new VehicleEntity("Hunday", VehicleType.BUS);
        vehicleService.save(vehicleEntity);

        vehicleEntity = new VehicleEntity("Chernomorec", VehicleType.TRAIN);
        vehicleService.save(vehicleEntity);
        vehicleEntity = new VehicleEntity("Kamenyar", VehicleType.TRAIN);
        vehicleService.save(vehicleEntity);
        vehicleEntity = new VehicleEntity("Oriental Express", VehicleType.TRAIN);
        vehicleService.save(vehicleEntity);
        vehicleEntity = new VehicleEntity("Kashtan", VehicleType.TRAIN);
        vehicleService.save(vehicleEntity);*/

    }

    @Test
    void anull(){
        QueryParam param =null;
        Page<VehicleEntity> page;
        page = vehicleService.getFilteredPaged(param);
        page.forEach(System.out::println);
        assertEquals(8, page.getSize());
    }

}