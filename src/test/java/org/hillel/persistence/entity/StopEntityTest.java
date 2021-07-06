package org.hillel.persistence.entity;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Set;

public class StopEntityTest {

    @Test
    void Initiate() {
        LocalDateTime dateTime = LocalDateTime.of(0, 1, 1, 2, 0);
        Instant instant = dateTime.atZone(ZoneId.of("Europe/Kiev")).toInstant();
        System.out.println(instant);

        Instant ins = Instant.ofEpochSecond(0);
        System.out.println(ins);
    }

    @Test
    void duraTion() {
        Duration dur = Duration.ofSeconds(0);
    }


}
