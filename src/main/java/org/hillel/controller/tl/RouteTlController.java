package org.hillel.controller.tl;

import org.hillel.dto.converter.RouteMapper;
import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.dto.QueryParam;
import org.hillel.dto.dto.RouteDto;
import org.hillel.dto.dto.StationDto;
import org.hillel.persistence.entity.RouteEntity;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.enums.VehicleType;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RouteTlController {
    private final TicketClient ticketClient;
    private final RouteMapper mapper;
    private final StationMapper stationMapper;
    int TABLE_SIZE = 7;
    private final QueryParam queryParam;
    private String rootUrl;

    @Autowired
    public RouteTlController(TicketClient ticketClient, StationMapper stationMapper, RouteMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        this.stationMapper = stationMapper;
        queryParam = new QueryParam();
    }

    @GetMapping("/routes")
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
        long totalPges = ticketClient.countRoutes() / queryParam.getPageSize() + 1;
        model.addAttribute("pager", (queryParam.getPageNumber() + 1) + " / " + totalPges);

        rootUrl = request.getRequestURL().toString();

        model.addAttribute("url", "route/");
        model.addAttribute("title", "Stations");
        model.addAttribute("pageInfo", "Маршруты");
        String[] tableHead = new String[]{"ID", "Route #", "From", "To", "Period", "Departure", "Vehicle"};
        model.addAttribute("headers", tableHead);

        Page<RouteEntity> allEntities = ticketClient.getFilteredPagedRoutes(queryParam);
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

    @GetMapping("/route/next")
    public RedirectView showNext(Model model) {
        if (queryParam.getPageNumber() < ticketClient.countRoutes() / queryParam.getPageSize())
            queryParam.nextPage();
        return new RedirectView(rootUrl);
    }

    @GetMapping("/route/prev")
    public RedirectView showPrev(Model model) {
        queryParam.previousPage();
        return new RedirectView(rootUrl);
    }

}
/*
http://localhost:8080/tl/routes?sortColumn=id&sortDirection=desc&pageNumber=0&pageSize=3
http://localhost:8080/tl/routes?sortColumn=id&sortDirection=asc&pageNumber=0&pageSize=25
http://localhost:8080/tl/routes?pageSize=6&sortColumn=name&sortDirection=desc
http://localhost:8080/tl/routes?pageSize=6&sortColumn=id&pageNumber=1
http://localhost:8080/tl/routes?pageNumber=2
*/