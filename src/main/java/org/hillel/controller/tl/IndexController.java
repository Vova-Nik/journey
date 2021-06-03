package org.hillel.controller.tl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String homePage() {
        return "index";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }
}
