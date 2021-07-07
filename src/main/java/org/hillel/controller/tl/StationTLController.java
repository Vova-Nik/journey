package org.hillel.controller.tl;

import org.hillel.dto.converter.RouteMapper;
import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.converter.StopMapper;
import org.hillel.dto.dto.QueryParam;
import org.hillel.dto.dto.StationDto;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StationTLController {
    private final TicketClient ticketClient;
    private final StationMapper stationMapper;
    private final StopMapper stopMapper;
    private final int TABLE_SIZE = 6;
    private final QueryParam queryParam;
    private String rootUrl;

    @Autowired
    public StationTLController(TicketClient ticketClient, StationMapper stationMapper, StopMapper stopMapper) {
        this.ticketClient = ticketClient;
        this.stationMapper = stationMapper;
        this.stopMapper = stopMapper;
        queryParam = new QueryParam();
    }

    @GetMapping("/stations")
    public String homeVehiclePage(Model model, HttpServletRequest request,
                                  @RequestParam(required = false) String sortColumn,
                                  @RequestParam(required = false) String sortDirection,
                                  @RequestParam(required = false) String filterField,
                                  @RequestParam(required = false) String filterValue,
                                  @RequestParam(required = false) String pageNumber,
                                  @RequestParam(required = false) String pageSize,
                                  @RequestParam(required = false) String filterOperation
    ) {
        queryParam.buildQueryParam(
                sortColumn,
                sortDirection,
                filterField,
                filterValue,
                pageNumber,
                pageSize,
                filterOperation
        );
        long totalPges = ticketClient.countStations() / queryParam.getPageSize() + 1;
        model.addAttribute("pager", (queryParam.getPageNumber() + 1) + " / " + totalPges);

        rootUrl = request.getRequestURL().toString();
        model.addAttribute("url", "station/");
        model.addAttribute("title", "Stations");
        model.addAttribute("pageInfo", "Станции");

        String[] tableHead = new String[]{"ID", "name", "type", "long", "lat", "founded in"};
        model.addAttribute("headers", tableHead);

        Page<StationEntity> allEntities = ticketClient.getFilteredPagedStations(queryParam);
        List<String[]> allEntitiesDto = allEntities.stream()
                .map(stationMapper::stationToStationDto)
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
        StationDto dto = stationMapper.stationToStationDto(entity);

        String pageInfo = "Станция id = " + id;
        ModelAndView mav = new ModelAndView("station_view");
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("entity", dto);

        Set<StopEntity> stops = ticketClient.getAllStopsByStation(entity);
//        Set<RouteEntity> routes = ticketClient.getConnectedRoutes(entity);
        List<String> stopDtos = new ArrayList<>();
        stops.forEach(stop -> stopDtos.add(stopMapper.stopToStopDto(stop).show()));
        mav.addObject("stops", stopDtos);
        return mav;
    }

    @GetMapping("/station/next")
    public RedirectView showNext(Model model) {
        if (queryParam.getPageNumber() < ticketClient.countStations() / queryParam.getPageSize())
            queryParam.nextPage();
        return new RedirectView(rootUrl);
    }

    @GetMapping("/station/prev")
    public RedirectView showPrev(Model model) {
        queryParam.previousPage();
        return new RedirectView(rootUrl);
    }
}
/*
http://localhost:8080/tl/stations?sortColumn=id&sortDirection=desc&pageNumber=0&pageSize=3
http://localhost:8080/tl/stations?pageSize=6&sortColumn=name&sortDirection=desc
http://localhost:8080/tl/stations?pageSize=6&sortColumn=id&pageNumber=1
http://localhost:8080/tl/stations?pageNumber=2
*/