package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Doctor;
import com.webService.appointmentScheduling.entities.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientsRepository extends JpaRepository<Patients, Long> {
    Optional<Patients> findByCpf(String cpf);

}
