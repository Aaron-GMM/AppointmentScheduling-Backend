package com.webService.appointmentScheduling.web.controller;

import com.webService.appointmentScheduling.DTO.patients.PatientsRequestDTO;
import com.webService.appointmentScheduling.DTO.patients.PatientsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.webService.appointmentScheduling.service.PatientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientsController {
    @Autowired
    private PatientsService patientsService;

    @GetMapping("/all")
    public ResponseEntity<List<PatientsResponseDTO>> findAll(){
        try{
            List<PatientsResponseDTO> patients = patientsService.findAll();
            return ResponseEntity.ok().body(patients);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

}
