package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import java.util.List;

class ClientServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static RouteService routeService;
    static VehicleService vehicleService;
    static JourneyService journeyService;
    static UserService clientService;

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        vehicleService = applicationContext.getBean(VehicleService.class);
        stationService = applicationContext.getBean(org.hillel.service.StationService.class);
        routeService = applicationContext.getBean(RouteService.class);
        clientService = applicationContext.getBean(UserService.class);
    }


    @Test
    void initClients() {

        clientService.save(new UserEntity("Joey", "Kramer", "Kramer@gmail.com"));
        clientService.save(new UserEntity("Frank", "Sinatra", "Sinatra@gmail.com"));
        clientService.save(new UserEntity("James", "Hetfield", "Hetfield@gmail.com"));
        clientService.save(new UserEntity("Lars", "Ulrich", "Ulrich@gmail.com"));
        clientService.save(new UserEntity("Kirk", "Hammett", "Hammett@gmail.com"));
        clientService.save(new UserEntity("Robert", "Trujillo", "Trujillo@gmail.com"));
        clientService.save(new UserEntity("Tarja", "Turunen", "Turunen@gmail.com"));
    }

    @Test
    void complex() {
        QueryParam param = new QueryParam();
//        param.setFilterValue(" ");
//        param.setFilterKey("name");
//        param.setFilterOperation("more");
//        param.setSortColumn("name");

//        List<ClientEntity> page = clientService.getComplex(param);
//        System.out.println(page);
        Page<UserEntity> page;

        param.setPageSize(5);
        param.setPageNumber(0);
        page = clientService.getFilteredPaged(param);
        page.forEach(System.out::println);

        param.setPageSize(8);
        param.setPageNumber(1);
        page = clientService.getFilteredPaged(param);
        page.forEach(System.out::println);

        param.setPageSize(8);
        param.setPageNumber(2);
        page = clientService.getFilteredPaged(param);
        page.forEach(System.out::println);

        assertEquals(8, page.getSize());
        try{
            param.setSortColumn("fgfsdffgsefgsefgsefg");
            page = clientService.getFilteredPaged(param);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e.getMessage().contains("Insufficient column name for sorting of Client"));
        }

        param.setPageSize(6);
        param.setPageNumber(2);
        param.setSortColumn("id");

        param.setPageSize(8);
        param.setPageNumber(0);
        page = clientService.getFilteredPaged(param);
        page.forEach(System.out::println);

        param.setPageSize(4);
        param.setPageNumber(4);
        page = clientService.getFilteredPaged(param);
        page.forEach(System.out::println);


    }

    @Test
    void filter(){
        QueryParam param = new QueryParam();
        param.setFilterValue("Bob");
        Page<UserEntity> page;
        page = clientService.getFilteredPaged(param);
        page.forEach(System.out::println);
    }

    @Test
    void anull(){
        QueryParam param =null;
        Page<UserEntity> page;
        page = clientService.getFilteredPaged(param);
        page.forEach(System.out::println);
        assertEquals(8, page.getSize());
    }
}

