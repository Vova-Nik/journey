package org.hillel.controller.rest;

import io.swagger.annotations.ApiOperation;
import org.hillel.dto.converter.JourneyMapper;
import org.hillel.dto.dto.JourneyDto;
import org.hillel.dto.dto.ProtoJourneyDto;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.TripEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @ApiOperation(value = "just return journey", tags = "journey")
    public ResponseEntity<List<JourneyDto>> test() {
        List<JourneyDto> dtos = new ArrayList<>();
        TripEntity trip = ticketClient.getTripById(2L);
        JourneyDto dto = new JourneyDto(trip, "Odessa", "Kyiv");
        dtos.add(dto);
        dtos.add(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /* **************************** find journey variamnts ****************************************/
    @PostMapping(
            path = "/jour",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(value = "find journeys by date, station from, station to" +
            "{     \"departureDate\": \"2021-07-02\",\n" +
            "      \"stationFrom\": \"Odessa\",\n" +
            "      \"stationTo\": \"Kyiv\"}", tags = "journey")

    public ResponseEntity<List<JourneyDto>> findJourneyVariants(@RequestBody ProtoJourneyDto protoJourneyDto) {
        List<JourneyDto> result = ticketClient.findJourneys(protoJourneyDto);
        if (result.size() == 0) {
            result.add(new JourneyDto(""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

/*
http://localhost:8080/api/swagger-ui.html

{
 "departureDate": "2021-07-19",
 "stationFrom": "Zhmerynka",
 "stationTo": "Kyiv"
}
*/
