package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.DTO.doctor.DoctorRequestDTO;
import com.webService.appointmentScheduling.DTO.doctor.DoctorResponseDTO;
import com.webService.appointmentScheduling.service.doctorService;
import com.webService.appointmentScheduling.service.exceptions.DoctorCreationException;
import com.webService.appointmentScheduling.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/doctor")
public class doctorController {

    @Autowired
    private doctorService service;

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDTO>> findAll(){
        try {
            List<DoctorResponseDTO> list_doctor = service.findAll();
            return ResponseEntity.ok().body(list_doctor);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
        }

    @PostMapping("/register")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        try {
            DoctorResponseDTO doctorResponse = service.insert(doctorRequestDTO);
            return ResponseEntity.ok(doctorResponse);
        }catch (DoctorCreationException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi Possivel Registrar o Doutor:" + e.getMessage());
        }
            }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            DoctorResponseDTO doctorResponseDTO = service.findById(id);
            return ResponseEntity.ok(doctorResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getDoctorByCpf(@PathVariable String cpf) {
        try {
            DoctorResponseDTO doctorResponseDTO = service.findByCpf(cpf);
            return ResponseEntity.ok(doctorResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    }