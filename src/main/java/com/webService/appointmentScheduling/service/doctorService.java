package com.webService.appointmentScheduling.service;

import com.webService.appointmentScheduling.repositories.doctorRepository;
import com.webService.appointmentScheduling.entities.Doctor;
import com.webService.appointmentScheduling.service.exceptions.DatabaseException;
import com.webService.appointmentScheduling.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class doctorService {

    @Autowired
    private doctorRepository repository;

    public List<Doctor> findAll(){
        return repository.findAll();
    }


    public Doctor findById(Long id){
        Optional<Doctor> obj = repository.findById(id);
        return obj.orElseThrow(()->new ResourceNotFoundException(id));

    }

    public Doctor insert(Doctor doctor){
        return  repository.save(doctor);
    }

    public Doctor udpadte(Long id, Doctor newDoctor){
        try {
            Doctor entity  = repository.getReferenceById(id);
            updateData(entity, newDoctor);
            return repository.save(entity);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }
    public void updateData(Doctor entity, Doctor docObj){
        entity.setName(docObj.getName());
        entity.setCpf(docObj.getCpf());
        entity.setDataNascimento(docObj.getDataNascimento());
        entity.setTell(docObj.getTell());
        entity.setSpecialization(docObj.getSpecialization());
    }

    public void delete(Long id){
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new DatabaseException(e.getMessage());
        }
        }
    }
