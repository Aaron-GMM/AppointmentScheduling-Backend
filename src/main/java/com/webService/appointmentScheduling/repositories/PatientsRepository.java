package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepository extends JpaRepository<Patients, Long> {
}
