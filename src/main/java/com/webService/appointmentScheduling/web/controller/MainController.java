package com.webService.appointmentScheduling.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/administrative")
    public String acessarPrincipal(){
        return "administrative/home";
    }
}
