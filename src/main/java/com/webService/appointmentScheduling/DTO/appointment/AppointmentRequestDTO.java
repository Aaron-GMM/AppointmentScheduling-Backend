package com.webService.appointmentScheduling.DTO.appointment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalTime time;
    private LocalDate date;
    private String description;
    private Long patientId;
    private Long doctorId;

    public AppointmentRequestDTO() {}

    public AppointmentRequestDTO(LocalTime time, LocalDate date, String description, Long patientId, Long doctorId) {
        this.time = time;
        this.date = date;
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    // Getters e Setters
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
