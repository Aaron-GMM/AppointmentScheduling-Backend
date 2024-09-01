package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface appointmentRepository extends JpaRepository<Appointment, Long> {
        List<Appointment> findByDoctorAndDateAndTime(Long doctorId, LocalDate date, LocalTime hora);

        List<Appointment> findByPatientAndDateAndTime(Long patientId, LocalDate date, LocalTime time);
}
