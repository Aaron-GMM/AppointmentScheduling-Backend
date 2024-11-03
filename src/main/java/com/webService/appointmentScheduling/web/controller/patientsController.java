package com.webService.appointmentScheduling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.webService.appointmentScheduling.service.patientsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patients")
public class patientsController {
    @Autowired
    private patientsService patientsService;




}
