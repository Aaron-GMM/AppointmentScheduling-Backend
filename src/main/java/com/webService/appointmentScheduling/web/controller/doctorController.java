package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.DTO.doctor.DoctorRequestDTO;
import com.webService.appointmentScheduling.DTO.doctor.DoctorResponseDTO;
import com.webService.appointmentScheduling.service.doctorService;
import com.webService.appointmentScheduling.service.exceptions.DatabaseException;
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

    @PutMapping("/id/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorRequestDTO requestDTO){
        DoctorResponseDTO  responseDTO = service.update(id, requestDTO);
        if(responseDTO != null){
            return  ResponseEntity.ok(responseDTO);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletDoctor(@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException erroNtF) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroNtF.getMessage());
        }catch (DatabaseException erroDb){
            return  ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Erro de disponibilidade do Bancod de Dados:"+erroDb.getMessage());
        }catch (Exception errouUnknow){
            return ResponseEntity.status(520).body("Erro Desconhecido:"+errouUnknow.getMessage());
        }
    }


    }