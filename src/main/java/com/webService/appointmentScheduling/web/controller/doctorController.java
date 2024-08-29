package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.service.doctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping( value = "/api")
public class doctorController {

    @Autowired
    private doctorService service;



}
