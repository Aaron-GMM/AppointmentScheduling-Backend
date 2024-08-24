package com.webService.appointmentScheduling.entities;

import jakarta.persistence.Entity;

@Entity
public class doctor {
    private Long id;
    private String name;
    private String specialization;
    private String login;
    private String password;
    private String role;

}
