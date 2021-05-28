package org.hillel.controller.tl;

import org.hillel.dto.converter.ClientMapper;
import org.hillel.dto.dto.ClientDto;
import org.hillel.persistence.entity.ClientEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientTLController {

    private final TicketClient ticketClient;
    private final ClientMapper mapper;
    private final int TABLE_SIZE = 4;

    @Autowired
    public ClientTLController(TicketClient ticketClient, ClientMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
    }

    @GetMapping("/clients")
    public String showClients(Model model) {
        model.addAttribute("url", "clients/");
        model.addAttribute("title", "Clients");
        model.addAttribute("pageInfo", "Список клиентов");
        List<ClientEntity> allEntities = ticketClient.getAllClients();

        String[] tableHead = new String[]{"ID", "name", "surname", "e-mail"};
        model.addAttribute("headers", tableHead);

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

    @GetMapping("/clients/{clientId}")
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
}

