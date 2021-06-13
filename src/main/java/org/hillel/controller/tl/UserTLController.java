package org.hillel.controller.tl;

import org.hillel.dto.converter.UserMapper;
import org.hillel.dto.dto.QueryParam;
import org.hillel.dto.dto.UserDto;
import org.hillel.persistence.entity.UserEntity;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserTLController {

    private final TicketClient ticketClient;
    private final UserMapper mapper;
    private final int TABLE_SIZE = 4;
    private final QueryParam queryParam;
    private String rootUrl;

    @Autowired
    public UserTLController(TicketClient ticketClient, UserMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        queryParam = new QueryParam();
    }

    @GetMapping("/users")
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
        model.addAttribute("url", "user/");
        model.addAttribute("title", "Clients");
        model.addAttribute("pageInfo", "Список клиентов");

        long totalPges = ticketClient.countUsers() / queryParam.getPageSize() + 1;
        model.addAttribute("pager", (queryParam.getPageNumber() + 1) + " / " + totalPges);

        String[] tableHead = new String[]{"ID", "name", "surname", "e-mail"};
        model.addAttribute("headers", tableHead);

        Page<UserEntity> allEntities = ticketClient.getFilteredPagedUsers(queryParam);

        List<String[]> allEntitiesDto = allEntities.stream()
                .map(mapper::userToDto)
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

    @GetMapping("/user/{userId}")
    public ModelAndView showById(Model model, @PathVariable("userId") Long userId) {

        UserEntity client = ticketClient.getUserById(userId);
        UserDto usertDto = mapper.userToDto(client);
        if (usertDto.getPwd().length() > 3) {
            usertDto.setPwd("******");
        } else
            usertDto.setPwd("not set");
        String pageInfo = "Клиент id = " + userId;
        ModelAndView mav = new ModelAndView("client_view");
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("entity", usertDto);
        return mav;
    }

    @GetMapping("/user/next")
    public RedirectView showNext(Model model, HttpServletRequest request) {
        if (queryParam.getPageNumber() < ticketClient.countUsers() / queryParam.getPageSize())
            queryParam.nextPage();
        return new RedirectView(rootUrl);
    }

    @GetMapping("/user/prev")
    public RedirectView showPrev(Model model, HttpServletRequest request) {
        queryParam.previousPage();
        return new RedirectView(rootUrl);
    }
}

/*
http://localhost:8080/tl/users?sortColumn=id&sortDirection=desc&pageNumber=0&pageSize=25
http://localhost:8080/tl/users?pageSize=6&sortColumn=name&sortDirection=desc
http://localhost:8080/tl/users?pageSize=6&sortColumn=id&pageNumber=1
http://localhost:8080/tl/users?pageNumber=2
*/
