package com.webService.appointmentScheduling.service;

import com.webService.appointmentScheduling.repositories.doctorRepository;
import com.webService.appointmentScheduling.entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class doctorService {

    @Autowired
    private doctorRepository repository;


}
