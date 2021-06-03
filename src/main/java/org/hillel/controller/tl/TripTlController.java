package org.hillel.controller.tl;

import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.converter.TripMapper;
import org.hillel.dto.dto.QueryParam;
import org.hillel.dto.dto.TripDto;
import org.hillel.persistence.entity.StationEntity;
import org.hillel.persistence.entity.TripEntity;
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
import java.util.stream.Collectors;

@Controller
public class TripTlController {
    private final TicketClient ticketClient;
    private final TripMapper mapper;
    private  final StationMapper stationMapper;
    private final int TABLE_SIZE = 8;
    private final QueryParam queryParam;
    private String rootUrl;

    @Autowired
    public TripTlController(TicketClient ticketClient, TripMapper tripMapper, StationMapper stationMapper) {
        this.ticketClient = ticketClient;
        this.mapper = tripMapper;
        this.stationMapper = stationMapper;
        queryParam = new QueryParam();
    }

    @GetMapping("/trips")
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

        long totalPges = ticketClient.countTrips() / queryParam.getPageSize() + 1;
        model.addAttribute("pager", (queryParam.getPageNumber() + 1) + " / " + totalPges);
        rootUrl = request.getRequestURL().toString();

        model.addAttribute("url", "trip/");
        model.addAttribute("title", "Stations");
        model.addAttribute("pageInfo", "Рейсы");

        String[] tableHead = new String[]{"ID", "Name", "Дата", "Время", "Мест всего", "Мест свободных", "Маршрут", "Транспорт"};
        model.addAttribute("headers", tableHead);

        Page<TripEntity> allEntities = ticketClient.getFilteredPagedTrips(queryParam);
        List<String[]> allEntitiesDto = allEntities.stream()
                .map(mapper::tripTotripDto)
                .map(dto -> {
                    String[] responseRow = new String[TABLE_SIZE];
                    responseRow[0] = dto.getId().toString();
                    responseRow[1] = dto.getName();
                    responseRow[2] = String.valueOf(dto.getDepartureDate());
                    responseRow[3] = String.valueOf(dto.getRoute().getDepartureTime());
                    responseRow[4] = String.valueOf(dto.getTickets());
                    responseRow[5] = String.valueOf(dto.getFreePlaces());
                    responseRow[6] = String.valueOf(dto.getRoute().getStationFrom() + " -> " + dto.getRoute().getStationTo());
                    responseRow[7] = String.valueOf(dto.getVehicle().getVehicleType() +" - " + dto.getVehicle().getName());
                    return responseRow;
                })
                .collect(Collectors.toList());
        model.addAttribute("entities", allEntitiesDto);
        return "common_view";
    }

    @GetMapping("/trip/{id}")
    public ModelAndView showById(Model model, @PathVariable("id") Long id) {

        TripEntity entity = ticketClient.getTripById(id);
        TripDto dto = mapper.tripTotripDto(entity);
        String pageInfo = "Рейс id = " + id;
        ModelAndView mav = new ModelAndView("trip_view");
        mav.addObject("pageInfo", pageInfo);

        mav.addObject("name", dto.getName());
        mav.addObject("departure", dto.getDeparture());
        mav.addObject("arrival", dto.getArrival());
        mav.addObject("tickets", String.valueOf(dto.getTickets()));
        mav.addObject("places", String.valueOf(dto.getFreePlaces()));
        mav.addObject("route", String.valueOf(dto.getRoute().getStationFrom() + " -> " + dto.getRoute().getStationTo()));
        mav.addObject("vehicle", String.valueOf(dto.getVehicle().getVehicleType() +" - " + dto.getVehicle().getName()));

        List<StationEntity> stations = ticketClient.getRouteStations(entity.getRoute().getId());
        List<String> stationDtos = new ArrayList<>();
        stations.forEach(station -> stationDtos.add(stationMapper.stationToStationDto(station).toString()));
        mav.addObject("stations", stationDtos);
        return mav;
    }

    @GetMapping("/trip/next")
    public RedirectView showNext(Model model) {
        if (queryParam.getPageNumber() < ticketClient.countTrips() / queryParam.getPageSize())
            queryParam.nextPage();
        return new RedirectView(rootUrl);
    }

    @GetMapping("/trip/prev")
    public RedirectView showPrev(Model model) {
        queryParam.previousPage();
        return new RedirectView(rootUrl);
    }
}
/*
http://localhost:8080/tl/trips?sortColumn=id&sortDirection=desc&pageNumber=0&pageSize=7
http://localhost:8080/tl/trips?pageSize=6&sortColumn=name&sortDirection=desc
http://localhost:8080/tl/trips?pageSize=6&sortColumn=id&pageNumber=1
http://localhost:8080/tl/trips?pageNumber=2
*/