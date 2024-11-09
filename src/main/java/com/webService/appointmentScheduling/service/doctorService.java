package com.webService.appointmentScheduling.service;

import com.webService.appointmentScheduling.DTO.doctor.DoctorRequestDTO;
import com.webService.appointmentScheduling.DTO.doctor.DoctorResponseDTO;
import com.webService.appointmentScheduling.repositories.doctorRepository;
import com.webService.appointmentScheduling.entities.Doctor;
import com.webService.appointmentScheduling.service.exceptions.DatabaseException;
import com.webService.appointmentScheduling.service.exceptions.DoctorCreationException;
import com.webService.appointmentScheduling.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class doctorService {

    @Autowired
    private doctorRepository repository;

    public List<DoctorResponseDTO> findAll() {
        List<Doctor> doctors = repository.findAll();
        return doctors.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    public Doctor findByIdEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public DoctorResponseDTO findById(Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("\nDoctor with ID " + id + " not found"));
        return convertToResponseDTO(doctor);
    }

    public DoctorResponseDTO findByCpf(String cpf) {
        Doctor doctor = repository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("\nDoctor with CPF " + cpf + " not found"));
        return convertToResponseDTO(doctor);
    }
    public DoctorResponseDTO insert(DoctorRequestDTO doctorRequestDTO) {
        try {
            Doctor doctor = convertToEntity(doctorRequestDTO);
            doctor = repository.save(doctor);
            return convertToResponseDTO(doctor);
        }catch (Exception e){
            throw new DoctorCreationException("Failed to create doctor:" +e.getMessage());
        }
        }

    public DoctorResponseDTO update(Long id, DoctorRequestDTO doctorRequestDTO) {
        try {
            Doctor entity = repository.getReferenceById(id);
            if (doctorRequestDTO == null){
                throw new IllegalArgumentException("Doctor cannot be Null");
            }
                updateData(entity, doctorRequestDTO);
                entity = repository.save(entity);
                return convertToResponseDTO(entity);

        }  catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Doctor with ID " + id + " not found.");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation while updating Doctor with ID " + id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data provided for Doctor update: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(Doctor entity, DoctorRequestDTO doctorRequestDTO) {
        entity.setName(doctorRequestDTO.getName());
        entity.setCpf(doctorRequestDTO.getCpf());
        entity.setDataNascimento(doctorRequestDTO.getDataNascimento());
        entity.setTell(doctorRequestDTO.getTell());
        entity.setSpecialization(doctorRequestDTO.getSpecialization());
    }

    private Doctor convertToEntity(DoctorRequestDTO doctorRequestDTO) {
        return new Doctor(null, doctorRequestDTO.getName(), doctorRequestDTO.getTell(),
                doctorRequestDTO.getCpf(), doctorRequestDTO.getSpecialization(), doctorRequestDTO.getDataNascimento());
    }

    private DoctorResponseDTO convertToResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(doctor.getId(), doctor.getName(), doctor.getTell(),
                doctor.getCpf(), doctor.getSpecialization(), doctor.getDataNascimento());
    }
}
