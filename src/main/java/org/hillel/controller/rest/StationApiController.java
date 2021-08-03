package org.hillel.controller.rest;

import io.swagger.annotations.ApiOperation;
import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.dto.StationDto;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.SynonimEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
            path = "/station",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(value = "get all stations", tags = "station")
    public ResponseEntity<List<StationDto>> stations() {
        List<StationEntity> stations = ticketClient.getAllStations();
        List<StationDto> dtos = stations.stream()
                .map(mapper::stationToStationDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    /* ***************************** get by id *********************/
    @GetMapping(
            path = "/station/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(value = "get exception min  station controller", tags = "station")
    public ResponseEntity<StationDto> getException(@PathVariable final Long id) {
        StationEntity station = ticketClient.getStationById(id);
        StationDto stdto = mapper.stationToStationDto(station);
        return ResponseEntity.status(HttpStatus.OK).body(stdto);
    }

    /* ***************************** get station names abreviations*********************/
    @GetMapping(
            path = "/abreviations",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(value = "get station names with abreviations", tags = "station")
    public ResponseEntity<Map<String, String>> getStationAbr() {
        List<SynonimEntity> synonims = ticketClient.getStationInfo();
        Map<String, String> rezult = new TreeMap<>();
        synonims.forEach(syn->rezult.put(syn.getTrueName(), syn.getName()));
        return ResponseEntity.status(HttpStatus.OK).body(rezult);
    }


    /* ******************** Station Synonim List *********************/
    @GetMapping(
            path = "/synonims",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiOperation(value = "get Station Synonim List", tags = "station")
    public ResponseEntity<Map<String, String>> getStationSynonimList() {
        return ResponseEntity.status(HttpStatus.OK).body(ticketClient.getStationSynonimList());
    }

}