package org.hillel.controller.rest;

import io.swagger.annotations.ApiOperation;
import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.dto.StationDto;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController

@RequestMapping("/station")
public class StationApiController {

    private final TicketClient ticketClient;
    private final StationMapper mapper;

    @Autowired
    public StationApiController(TicketClient ticketClient, StationMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
    }

    /* *************************************** get all *********************************************/
    @GetMapping(
            path = "/stations",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(value = "get all stations", tags = "stations")
    @ResponseBody
    public ResponseEntity<List<StationDto>> stations() {
        List<StationEntity> stations = ticketClient.getAllStations();
        List<StationDto> dtos = stations.stream()
                .map(mapper::stationToStationDto)
                .collect(Collectors.toList());
        System.out.println(dtos.get(0));
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

}