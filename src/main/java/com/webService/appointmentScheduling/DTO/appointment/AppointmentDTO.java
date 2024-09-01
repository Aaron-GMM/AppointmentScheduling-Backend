package com.webService.appointmentScheduling.DTO.appointment;

import java.io.Serializable;

public class AppointmentDTO implements Serializable {
    private  static final long serialVersionUID = 1L;

    private Long id;
    private String hora;
    private String date;
    private String description;
    private Long patientId;
    private Long doctorId;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id, String hora, String date, String description, Long patientId, Long doctorId) {
        this.id = id;
        this.hora = hora;
        this.date = date;
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
