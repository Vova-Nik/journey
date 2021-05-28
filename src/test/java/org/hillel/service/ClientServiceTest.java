package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.ClientEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static JourneyService journeyService;
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
    void initClients() {
/*        clientService.save(new ClientEntity("Bob", "Dilan", "Dilan@gmail.com"));
        clientService.save(new ClientEntity("David", "Gilmour", "Gilmour@gmail.com"));
        clientService.save(new ClientEntity("Jean-Michel", "Jarre", "Jarre@gmail.com"));
        clientService.save(new ClientEntity("Paul", "Mauriat", "Mauriat@gmail.com"));
        clientService.save(new ClientEntity("James", "Last", "Last@gmail.com"));
        clientService.save(new ClientEntity("Ritchie", "Blackmore", "Blackmore@gmail.com"));
        clientService.save(new ClientEntity("Mick", "Jagger", "Jagger@gmail.com"));
        clientService.save(new ClientEntity("Ronald-James", "Padavona", "Padavona@gmail.com"));
        clientService.save(new ClientEntity("Ozzy", "Osbourne", "Osbourne@gmail.com"));*/
    }
}
