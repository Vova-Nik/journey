package org.hillel.controller.tl;

import org.hillel.dto.converter.RouteMapper;
import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.dto.StationDto;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StationTLController {
    private final TicketClient ticketClient;
    private final StationMapper mapper;
    private final RouteMapper routeMapper;
    int TABLE_SIZE = 6;

    @Autowired
    public StationTLController(TicketClient ticketClient, StationMapper mapper, RouteMapper routeMapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        this.routeMapper=routeMapper;
    }

    @GetMapping("/stations")
    public String homeVehiclePage(Model model) {
        model.addAttribute("url", "station/");
        model.addAttribute("title", "Stations");
        model.addAttribute("pageInfo", "Станции");

        String[] tableHead = new String[]{"ID", "name", "type", "long", "lat", "founded in"};
        model.addAttribute("headers", tableHead);

        List<StationEntity> allEntities = ticketClient.getAllStations();
        List<String[]> allEntitiesDto = allEntities.stream()
                .map(mapper::stationToStationDto)
                .map(dto -> {
                    String[] responseRow = new String[TABLE_SIZE];
                    responseRow[0] = dto.getId().toString();
                    responseRow[1] = dto.getName();
                    responseRow[2] = String.valueOf(dto.getStationType());
                    responseRow[3] = String.valueOf(dto.getLongitude());
                    responseRow[4] = String.valueOf(dto.getLatitude());
                    responseRow[5] = String.valueOf(dto.getFoundationYear());
                    return responseRow;
                })
                .collect(Collectors.toList());
        model.addAttribute("entities", allEntitiesDto);
        return "common_view";
    }

    @GetMapping("/station/{id}")
    public ModelAndView showById(Model model, @PathVariable("id") Long id) {

        StationEntity entity = ticketClient.getStationById(id);
        StationDto dto = mapper.stationToStationDto(entity);

        String pageInfo = "Станция id = " + id;
        ModelAndView mav = new ModelAndView("station_view");
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("entity", dto);
        Set<RouteEntity> routes = ticketClient.getConnectedRoutes(id);
        List<String> routeDtos = new ArrayList<>();
        routes.forEach(route->routeDtos.add(routeMapper.routeToRouteDto(route).toString()));
        mav.addObject("routes",routeDtos);
        return mav;
    }
}
