package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.DTO.doctor.DoctorRequestDTO;
import com.webService.appointmentScheduling.DTO.doctor.DoctorResponseDTO;
import com.webService.appointmentScheduling.service.doctorService;
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
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponse = service.insert(doctorRequestDTO);
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO(doctorResponse.getId(), doctorResponse.getName(), doctorResponse.getTell(),
                doctorResponse.getCpf(), doctorResponse.getSpecialization(), doctorResponse.getDataNascimento());
        return ResponseEntity.ok(doctorResponseDTO);
    }

    
}
