package com.webService.appointmentScheduling.web.controller;

import com.webService.appointmentScheduling.DTO.patients.PatientsRequestDTO;
import com.webService.appointmentScheduling.DTO.patients.PatientsResponseDTO;
import com.webService.appointmentScheduling.service.exceptions.DatabaseException;
import com.webService.appointmentScheduling.service.exceptions.PatientsCreationException;
import com.webService.appointmentScheduling.service.exceptions.ResourceNotFoundException;
import com.webService.appointmentScheduling.web.dto.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.webService.appointmentScheduling.service.PatientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public ResponseEntity<?> createPatient(@Valid  @RequestBody PatientsRequestDTO patientsRequestDTO, BindingResult br){
        try{
            if (br.hasErrors()){
                List<String> errors = br.getFieldErrors().stream()
                        .map(f->f.getField()+": "+f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            PatientsResponseDTO patientsResponseDTO = patientsService.insert(patientsRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(patientsResponseDTO);
        }catch (PatientsCreationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Erro ao Resgitrar o paciente", e.getMessage()));
        }
    }

    @PostMapping("/cpf")
    public ResponseEntity<?> getPatientByCpf(@RequestBody PatientsRequestDTO patientsRequestDTO,BindingResult br){
        try{
            if (br.hasErrors()){
                List<String> errors = br.getFieldErrors().stream()
                        .map(f->f.getField()+": "+f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            PatientsResponseDTO patientsResponseDTO = patientsService.findByCpf(patientsRequestDTO.getCpf());
            return ResponseEntity.ok(patientsResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Paciente não encontrado", e.getMessage() ));
        }
    }

    @PostMapping("/name")
    public ResponseEntity<?> getPatientByName(@RequestBody PatientsRequestDTO patientsRequestDTO,BindingResult br){
        try {
            if (br.hasErrors()){
                List<String> errors = br.getFieldErrors().stream()
                        .map(f->f.getField()+": "+f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }

            PatientsResponseDTO patientsResponseDTO = patientsService.findByName(patientsRequestDTO.getName());
            return ResponseEntity.ok(patientsResponseDTO);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Paciente não encotrado", e.getMessage()));
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Long id){
        try {
            PatientsResponseDTO  patientsResponseDTO = patientsService.findById(id);
            return ResponseEntity.ok(patientsResponseDTO);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Paciente não encontrado", e.getMessage()));
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updatePatiente(@Valid @PathVariable Long id, @Valid @RequestBody PatientsRequestDTO patientsRequestDTO,BindingResult br){
        try{
            if (br.hasErrors()){
                List<String> errors = br.getFieldErrors().stream()
                        .map(f->f.getField()+": "+f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            PatientsResponseDTO patientsResponseDTO = patientsService.update(id, patientsRequestDTO);
            return ResponseEntity.ok(patientsResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Paciente não encontrado","ID " + id + "não existe"));
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Erro no Banco",e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Erro inesperado", e.getMessage()));
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id){
        try {
            patientsService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Paciente não encontrado","ID " + id + "não existe"));
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Erro no Banco",e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Erro inesperado", e.getMessage()));
        }
    }

}
