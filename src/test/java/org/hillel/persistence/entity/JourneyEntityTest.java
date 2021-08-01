package org.hillel.persistence.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JourneyEntityTest {
    @Test
    void create() {
        LocalTime lt =  LocalTime.of(1,10);
        System.out.println(lt.getSecond());
        System.out.println(lt.getMinute());
        System.out.println(lt.getHour());

    }


}
