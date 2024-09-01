package com.webService.appointmentScheduling.service;

import com.webService.appointmentScheduling.entities.Patients;
import com.webService.appointmentScheduling.service.exceptions.DatabaseException;
import com.webService.appointmentScheduling.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.webService.appointmentScheduling.repositories.patientsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class patientsService {

    @Autowired
    private patientsRepository repository;

    public List<Patients> findAll(){
        return  repository.findAll();
    }

    public Patients findById(Long id){
        Optional<Patients> obj = repository.findById(id);
        return obj.orElseThrow(()->new ResourceNotFoundException(id));
    }

    public Patients insert(Patients patients){
        return  repository.save(patients);
    }
    public Patients update(Long id, Patients newPatients){
        try {
            Patients entity = repository.getReferenceById(id);
            updateData(entity, newPatients);
            return repository.save(entity);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void updateData(Patients entity, Patients patientObj){
        entity.setName(patientObj.getName());
        entity.setCpf(patientObj.getCpf());
        entity.setDataNascimento(patientObj.getDataNascimento());
        entity.setTell(patientObj.getTell());

    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw  new DatabaseException(e.getMessage());
        }
    }
}
