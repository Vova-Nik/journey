package org.hillel.controller.tl;

import org.hillel.dto.converter.RouteMapper;
import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.dto.RouteDto;
import org.hillel.dto.dto.StationDto;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.enums.VehicleType;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RouteController {
    private final TicketClient ticketClient;
    private final RouteMapper mapper;
    private final StationMapper stationMapper;
    int TABLE_SIZE = 7;

    @Autowired
    public RouteController(TicketClient ticketClient, StationMapper stationMapper, RouteMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        this.stationMapper = stationMapper;
    }

    @GetMapping("/routes")
    public String homeVehiclePage(Model model) {
        model.addAttribute("url", "route/");
        model.addAttribute("title", "Stations");
        model.addAttribute("pageInfo", "Маршруты");
        String[] tableHead = new String[]{"ID", "Route #", "From", "To", "Period", "Departure", "Vehicle"};
        model.addAttribute("headers", tableHead);

        List<RouteEntity> allEntities = ticketClient.getAllRoutes();
        List<String[]> allEntitiesDto = allEntities.stream()
                .map(mapper::routeToRouteDto)
                .map(dto -> {
                    String[] responseRow = new String[TABLE_SIZE];
                    responseRow[0] = dto.getId().toString();
                    responseRow[1] = dto.getName();
                    responseRow[2] = String.valueOf(dto.getStationFrom());
                    responseRow[3] = String.valueOf(dto.getStationTo());
                    responseRow[4] = String.valueOf(dto.getDeparturePeriod());
                    responseRow[5] = String.valueOf(dto.getDepartureTime());
                    responseRow[6] = String.valueOf(dto.getType());
                    return responseRow;
                })
                .collect(Collectors.toList());
        model.addAttribute("entities", allEntitiesDto);
        return "common_view";
    }

    @GetMapping("/route/{id}")
    public ModelAndView showById(Model model, @PathVariable("id") Long id) {

        RouteEntity entity = ticketClient.getRouteById(id);
        RouteDto dto = mapper.routeToRouteDto(entity);

        String pageInfo = "Маршрут id = " + id;
        ModelAndView mav = new ModelAndView("route_view");
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("entity", dto);
        List<StationEntity> stations = ticketClient.getRouteStations(id);
        List<String> stationDtos = new ArrayList<>();
        stations.forEach(station -> stationDtos.add(stationMapper.stationToStationDto(station).toString()));
        mav.addObject("stations", stationDtos);
        return mav;
    }
}