package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface appointmentRepository extends JpaRepository<Appointment, Long> {
}
