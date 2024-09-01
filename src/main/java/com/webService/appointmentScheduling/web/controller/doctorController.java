package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.entities.Doctor;
import com.webService.appointmentScheduling.service.doctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/doctor")
public class doctorController {

    @Autowired
    private doctorService service;

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> findAll(){
        try {
            List<Doctor> list_doctor = service.findAll();
            return ResponseEntity.ok().body(list_doctor);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
        }


}
