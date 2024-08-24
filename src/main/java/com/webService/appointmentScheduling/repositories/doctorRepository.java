package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface doctorRepository extends JpaRepository<Doctor, Long> {
}
