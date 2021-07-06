package org.hillel.controller.rest;

import io.swagger.annotations.ApiOperation;
import org.hillel.dto.converter.JourneyMapper;
import org.hillel.dto.dto.JourneyDto;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.TripEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController

@RequestMapping("/journey")
public class JourneyAPIController {

    private final TicketClient ticketClient;
    private final JourneyMapper mapper;
    QueryParam queryParam;

    @Autowired
    public JourneyAPIController(TicketClient ticketClient, JourneyMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        queryParam = new QueryParam();
    }

    /* *************************************** test *********************************************/
    @GetMapping(
            path = "/test",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ApiOperation(value = "just return journey", tags = "journeys")
    @ResponseBody
    public ResponseEntity<List<JourneyDto>> test() {
        List<JourneyDto> dtos = new ArrayList<>();
        TripEntity trip = ticketClient.getTripById(1L);
        JourneyDto dto = new JourneyDto(trip, "Odessa", "Kyiv");

        dtos.add(dto);
        dtos.add(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /* *************************************** get all *********************************************/
    @PostMapping(
            path = "/find",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(value = "find journeys by date, station from, station to" +
            "{     \"departure\": \"2021-07-02\",\n" +
            "      \"stationFrom\": \"Odessa\",\n" +
            "      \"stationTo\": \"Kyiv\"}", tags = "journeys")
    @ResponseBody
    public ResponseEntity<List<JourneyDto>> findJourneyVariants(@RequestBody JourneyDto journeyDto) {
//    public ResponseEntity<List<JourneyDto>> findJourneyVariants(@RequestParam JourneyDto journeyDto) {
        if (StringUtils.isEmpty(journeyDto.getStationFrom()) || StringUtils.isEmpty(journeyDto.getStationTo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        LocalDate date = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(journeyDto.getDeparture(), formatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<JourneyDto> result = ticketClient.findJourneys(journeyDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
//http://localhost:8080/journey/api/swagger-ui.html
/*
http://localhost:8080/api/swagger-ui.html
[
{     "departure": "2021-07-02",
      "stationFrom": "Odessa",
      "stationTo": "Kyiv"}
]

        */
