package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
        @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId AND a.date = :date AND a.time = :time")
        List<Appointment> findByPatientAndDateAndTime(@Param("patientId") Long patientId, @Param("date") LocalDate date, @Param("time") LocalTime time);

        @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.date = :date AND a.time = :time")
        List<Appointment> findByDoctorAndDateAndTime(@Param("doctorId") Long doctorId, @Param("date") LocalDate date, @Param("time") LocalTime time);


        List<Appointment> findByDateAndTime(LocalDate date, LocalTime time);
}
