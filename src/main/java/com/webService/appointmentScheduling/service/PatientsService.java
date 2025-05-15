package com.webService.appointmentScheduling.service;

import com.webService.appointmentScheduling.DTO.patients.PatientsRequestDTO;
import com.webService.appointmentScheduling.DTO.patients.PatientsResponseDTO;
import com.webService.appointmentScheduling.entities.Patients;
import com.webService.appointmentScheduling.service.exceptions.DatabaseException;
import com.webService.appointmentScheduling.service.exceptions.DoctorCreationException;
import com.webService.appointmentScheduling.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.webService.appointmentScheduling.repositories.PatientsRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientsService {

    @Autowired
    private PatientsRepository repository;

    public List<PatientsResponseDTO> findAll() {
        List<Patients> patients = repository.findAll();
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    public PatientsResponseDTO findById(Long id){
         Patients p  = repository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("\nPatients with ID " + id + " not found"));
        return convertToResponseDTO(p);
    }
    public Patients findByIdEntity(Long id){
        return  repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
    }
    public PatientsResponseDTO findByCpf(String cpf) {
        Patients p = repository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("\nPatients with CPF " + cpf + " not found"));
        return convertToResponseDTO(p);
    }
    public PatientsResponseDTO findByName(String name){
        Patients p = repository.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException("\nPatients with Name " + name + " not found"));
        return convertToResponseDTO(p);
    }
    public PatientsResponseDTO insert(PatientsRequestDTO patientsRequestDTO){
        try {
            Patients p = convertToEntity(patientsRequestDTO);
                    p = repository.save(p);
                    return  convertToResponseDTO(p);
        } catch (Exception e) {
            throw new DoctorCreationException("Failed to create Patients:"+e.getMessage());
        }
    }
    public PatientsResponseDTO update(Long id, PatientsRequestDTO newPatientsDTO){
        try {
            Patients entity = repository.getReferenceById(id);
            if (newPatientsDTO == null){
                throw new IllegalArgumentException("Patients cannot be Null");
            }
            updateData(entity, newPatientsDTO);
            entity = repository.save(entity);

            return convertToResponseDTO(entity);
        }  catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Patients with ID " + id + " not found.");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation while updating Patients with ID " + id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data provided for Patients update: " + e.getMessage());
        }
    }

    public void updateData(Patients entity, PatientsRequestDTO patientsRequestDTO){
        entity.setName(patientsRequestDTO.getName());
        entity.setCpf(patientsRequestDTO.getCpf());
        entity.setDataNascimento(patientsRequestDTO.getDataNascimento());
        entity.setTell(patientsRequestDTO.getTell());

    }

    public void delete(Long id){
        Patients p = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Patients  with ID " + id + " not found"));

        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw  new DatabaseException("Integrity violation while deleting Patients with ID " + id);
        }
    }
    private Patients convertToEntity(PatientsRequestDTO patientsRequestDTO){
        return  new Patients( null,patientsRequestDTO.getName(),patientsRequestDTO.getCpf(),
                patientsRequestDTO.getTell(),patientsRequestDTO.getDataNascimento());
    }
    private PatientsResponseDTO convertToResponseDTO (Patients p){
        return  new PatientsResponseDTO(p.getId(), p.getName(), p.getCpf(),
                p.getTell(),p.getDataNascimento());
    }

}
