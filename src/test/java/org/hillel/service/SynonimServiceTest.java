package org.hillel.service;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.SynonimEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SynonimServiceTest {

    static ConfigurableApplicationContext applicationContext;
    static Environment env;
    static StationService stationService;
    static SynonimService synonimService;

    @BeforeAll

    public static void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        Environment env = applicationContext.getEnvironment();
        stationService = applicationContext.getBean(StationService.class);
        synonimService = applicationContext.getBean(SynonimService.class);
    }

    @Test
    void getStationSynonimList() {
        Map<String, String> mapa = synonimService.getStationSynonimList();
        System.out.println(mapa);
    }

    @Test
    void getStationInfo() {
        List<SynonimEntity> list = synonimService.getStationAbreviations();
        System.out.println(list);
        assertTrue(list.size()>10);
    }
}