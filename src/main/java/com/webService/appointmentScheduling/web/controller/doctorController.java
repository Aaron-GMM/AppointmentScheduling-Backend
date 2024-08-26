package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.service.doctorService;
import com.webService.appointmentScheduling.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( value = "/api")
public class doctorController {

    @Autowired
    private doctorService service;

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> findAll(){
        List<Doctor> list_doctor = service.findAll();
        return ResponseEntity.ok().body(list_doctor);
    }

}
