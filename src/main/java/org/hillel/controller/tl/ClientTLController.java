package org.hillel.controller.tl;

import org.hillel.dto.converter.ClientMapper;
import org.hillel.dto.dto.ClientDto;
import org.hillel.dto.dto.QueryParam;
import org.hillel.persistence.entity.ClientEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientTLController {

    private final TicketClient ticketClient;
    private final ClientMapper mapper;
    private final int TABLE_SIZE = 4;
    private final QueryParam queryParam;
    private String rootUrl;

    @Autowired
    public ClientTLController(TicketClient ticketClient, ClientMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        queryParam = new QueryParam();
    }

    @GetMapping("/clients")
    public String showClients(Model model, HttpServletRequest request,
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
        rootUrl = request.getRequestURL().toString();
        model.addAttribute("url", "client/");
        model.addAttribute("title", "Clients");
        model.addAttribute("pageInfo", "Список клиентов");

        long totalPges = ticketClient.countClients() / queryParam.getPageSize() + 1;
        model.addAttribute("pager", (queryParam.getPageNumber() + 1) + " / " + totalPges);

        String[] tableHead = new String[]{"ID", "name", "surname", "e-mail"};
        model.addAttribute("headers", tableHead);

        Page<ClientEntity> allEntities = ticketClient.getFilteredPagedClients(queryParam);

        List<String[]> allEntitiesDto = allEntities.stream()
                .map(mapper::clientToDto)
                .map(dto -> {
                    String[] responseRow = new String[TABLE_SIZE];
                    responseRow[0] = dto.getId().toString();
                    responseRow[1] = dto.getName();
                    responseRow[2] = dto.getSurname();
                    responseRow[3] = dto.getEmail();
                    return responseRow;
                })
                .collect(Collectors.toList());

        model.addAttribute("entities", allEntitiesDto);
        return "common_view";
    }



    @GetMapping("/client/{clientId}")
    public ModelAndView showById(Model model, @PathVariable("clientId") Long clientId) {

        ClientEntity client = ticketClient.getClientById(clientId);
        ClientDto clientDto = mapper.clientToDto(client);
        if (clientDto.getPwd().length() > 3) {
            clientDto.setPwd("******");
        } else
            clientDto.setPwd("not set");
        String pageInfo = "Клиент id = " + clientId;
        ModelAndView mav = new ModelAndView("client_view");
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("entity", clientDto);
        return mav;
    }

    @GetMapping("/client/next")
    public RedirectView showNext(Model model, HttpServletRequest request) {
        if (queryParam.getPageNumber() < ticketClient.countClients() / queryParam.getPageSize())
            queryParam.nextPage();
        return new RedirectView(rootUrl);
    }

    @GetMapping("/client/prev")
    public RedirectView showPrev(Model model, HttpServletRequest request) {
        queryParam.previousPage();
        return new RedirectView(rootUrl);
    }
}

/*
http://localhost:8080/tl/clients?sortColumn=id&sortDirection=desc&pageNumber=0&pageSize=25
http://localhost:8080/tl/clients?pageSize=6&sortColumn=name&sortDirection=desc
http://localhost:8080/tl/clients?pageSize=6&sortColumn=id&pageNumber=1
http://localhost:8080/tl/clients?pageNumber=2
*/
