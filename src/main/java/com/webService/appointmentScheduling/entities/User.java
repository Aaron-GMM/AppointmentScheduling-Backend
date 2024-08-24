package com.webService.appointmentScheduling.entities;

import jakarta.persistence.Entity;

@Entity
public class User {

    private Long id;
    private String nome;
    private String login;
    private String password;
    private String role;
}
