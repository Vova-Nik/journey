package org.hillel.controller.tl;

import org.hillel.dto.converter.VehicleMapper;
import org.hillel.dto.dto.QueryParam;
import org.hillel.dto.dto.VehicleDto;
import org.hillel.persistence.entity.UserEntity;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VehicleTLController {

    private final TicketClient ticketClient;
    private final VehicleMapper mapper;
    private final int TABLE_SIZE = 4;
    private final QueryParam queryParam;
    private String rootUrl;

    @Autowired
    public VehicleTLController(TicketClient ticketClient, VehicleMapper vehicleMapper) {
        this.ticketClient = ticketClient;
        this.mapper = vehicleMapper;
        queryParam = new QueryParam();
    }
    @GetMapping("/vehicles")
    public String homeVehiclePage(Model model, HttpServletRequest request,
                                  @RequestParam(required = false) String sortColumn,
                                  @RequestParam(required = false) String sortDirection,
                                  @RequestParam(required = false) String filterField,
                                  @RequestParam(required = false) String filterValue,
                                  @RequestParam(required = false) String pageNumber,
                                  @RequestParam(required = false) String pageSize,
                                  @RequestParam(required = false) String filterOperation
    ){
        queryParam.buildQueryParam(
                sortColumn,
                sortDirection,
                filterField,
                filterValue,
                pageNumber,
                pageSize,
                filterOperation
        );
        long totalPges = ticketClient.countVehicles() / queryParam.getPageSize() + 1;
        model.addAttribute("pager", (queryParam.getPageNumber() + 1) + " / " + totalPges);

        rootUrl = request.getRequestURL().toString();

        model.addAttribute("url", "vehicle/");
        model.addAttribute("title", "Stations");
        model.addAttribute("pageInfo", "Транспортные средства");

        String[] tableHead = new String[]{"ID", "Name", "Type", "Capacity"};
        model.addAttribute("headers", tableHead);

        Page<VehicleEntity> allEntities = ticketClient.getFilteredPagedVehicles(queryParam);
        List<String[]> allEntitiesDto = allEntities.stream()
                .map(mapper::vehicleToVehicleDto)
                .map(dto -> {
                    String[] responseRow = new String[TABLE_SIZE];
                    responseRow[0] = dto.getId().toString();
                    responseRow[1] = dto.getName();
                    responseRow[2] = String.valueOf(dto.getVehicleType());
                    responseRow[3] = String.valueOf(dto.getCapacity());
                    return responseRow;
                })
                .collect(Collectors.toList());

        model.addAttribute("entities", allEntitiesDto);

        return "common_view";
    }

    @GetMapping("/vehicle/{id}")
    public ModelAndView showById(Model model, @PathVariable("id") Long id) {

        VehicleEntity entity = ticketClient.getVehicleById(id);
        VehicleDto dto = mapper.vehicleToVehicleDto(entity);
        String pageInfo = "Транспортное средство id = " + id;
        ModelAndView mav = new ModelAndView("vehicle_view");
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("entity", dto);
        return mav;
    }

    @GetMapping("/vehicle/next")
    public RedirectView showNext(Model model) {
        if (queryParam.getPageNumber() < ticketClient.countVehicles() / queryParam.getPageSize())
            queryParam.nextPage();
        return new RedirectView(rootUrl);
    }

    @GetMapping("/vehicle/prev")
    public RedirectView showPrev(Model model) {
        queryParam.previousPage();
        return new RedirectView(rootUrl);
    }

}
/*
http://localhost:8080/tl/vehicles?sortColumn=id&sortDirection=desc&pageNumber=0&pageSize=3
http://localhost:8080/tl/vehicles?pageSize=6&sortColumn=name&sortDirection=desc
http://localhost:8080/tl/vehicles?pageSize=6&sortColumn=id&pageNumber=1
http://localhost:8080/tl/vehicles?pageNumber=2
*/