package ua.iamiluxa.timetrackingspringproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/index")
    public String getIndexPage() { return "index"; }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
