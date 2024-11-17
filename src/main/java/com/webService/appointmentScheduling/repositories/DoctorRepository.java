package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
       Optional<Doctor> findByCpf(String cpf);


}
