package com.webService.appointmentScheduling.DTO.appointment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalTime hora;
    private LocalDate date;
    private String description;
    private String patientName;
    private String doctorName;

    public AppointmentResponseDTO() {}

    public AppointmentResponseDTO(Long id, LocalTime hora, LocalDate date, String description, String patientName, String doctorName) {
        this.id = id;
        this.hora = hora;
        this.date = date;
        this.description = description;
        this.patientName = patientName;
        this.doctorName = doctorName;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
