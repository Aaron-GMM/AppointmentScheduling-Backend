package com.webService.appointmentScheduling.entities.user;

public record RegisterUserDTO(String nome, String login, String password, UserRole role) {
}
