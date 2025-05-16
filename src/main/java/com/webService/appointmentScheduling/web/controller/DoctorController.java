package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.DTO.doctor.DoctorRequestDTO;
import com.webService.appointmentScheduling.DTO.doctor.DoctorResponseDTO;
import com.webService.appointmentScheduling.service.DoctorService;
import com.webService.appointmentScheduling.service.exceptions.DatabaseException;
import com.webService.appointmentScheduling.service.exceptions.DoctorCreationException;
import com.webService.appointmentScheduling.service.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDTO>> findAll() {
        try {
            List<DoctorResponseDTO> doctors = doctorService.findAll();
            return ResponseEntity.ok().body(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createDoctor(@Valid @RequestBody DoctorRequestDTO doctorRequestDTO, BindingResult br) {
        try {
            if (br.hasErrors()){
                List<String> errors = br.getFieldErrors().stream()
                        .map(f ->f.getField()+": "+f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            DoctorResponseDTO doctorResponse = doctorService.insert(doctorRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponse);
        } catch (DoctorCreationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Erro ao registrar o doutor", e.getMessage()));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            DoctorResponseDTO doctorResponseDTO = doctorService.findById(id);
            return ResponseEntity.ok(doctorResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Doutor não encontrado", e.getMessage()));
        }
    }

    @PostMapping("/cpf/")
    public ResponseEntity<?> getDoctorByCpf(@Valid @RequestBody DoctorRequestDTO doctorRequestDTO,BindingResult br) {
        try {
            if (br.hasErrors()){
                List<String> errors = br.getFieldErrors().stream()
                        .map(f ->f.getField()+": "+f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            DoctorResponseDTO doctorResponseDTO = doctorService.findByCpf(doctorRequestDTO.getCpf());
            return ResponseEntity.ok(doctorResponseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Doutor não encontrado", e.getMessage()));
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO requestDTO, BindingResult br) {
        try {
            if (br.hasErrors()){
                List<String> errors = br.getFieldErrors().stream()
                        .map(f ->f.getField()+": "+f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            DoctorResponseDTO responseDTO = doctorService.update(id, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Doutor não encontrado", "ID " + id + " não existe"));
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Erro de banco de dados", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Erro inesperado", e.getMessage()));
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.delete(id);
            return ResponseEntity.noContent().build(); // Resposta 204 quando a exclusão for bem-sucedida
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Doutor não encontrado", "ID " + id + " não existe"));
        } catch (DatabaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Erro de banco de dados", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new com.webService.appointmentScheduling.web.dto.ErrorResponse("Erro inesperado", e.getMessage()));
        }
    }
    }