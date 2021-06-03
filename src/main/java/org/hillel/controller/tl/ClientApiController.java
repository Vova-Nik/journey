package org.hillel.controller.tl;

import org.hillel.dto.converter.ClientMapper;
import org.hillel.dto.converter.RouteMapper;
import org.hillel.dto.converter.StationMapper;
import org.hillel.dto.dto.FilterOperation;
import org.hillel.dto.dto.QueryParam;
import org.hillel.service.TicketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ClientApiController {

    private final TicketClient ticketClient;
    private final ClientMapper mapper;
    private final int TABLE_SIZE = 4;
    QueryParam queryParam;

    @Autowired
    public ClientApiController(TicketClient ticketClient, ClientMapper mapper) {
        this.ticketClient = ticketClient;
        this.mapper = mapper;
        queryParam = new QueryParam();
    }

    @PostMapping("/apiclients/text")
    public RedirectView showClientsPostText(
                                            @RequestParam(required = false) String sortColumn,
                                            @RequestParam(required = false) String sortDirection,
                                            @RequestParam(required = false) String filterField,
                                            @RequestParam(required = false) String filterValue,
                                            @RequestParam(required = false) String filterOperation,
                                            @RequestParam(required = false) int pageNumber,
                                            @RequestParam(required = false) int pageSize
    ) {

        System.out.println("/apiclients/text");
        System.out.println(sortColumn);
        System.out.println(sortDirection);
        System.out.println(filterField);
        System.out.println(filterValue);
        System.out.println(filterOperation);
        System.out.println(pageNumber);
        System.out.println(pageSize);

        return new RedirectView("http://127.0.0.1:5500/post.html");
    }

}
