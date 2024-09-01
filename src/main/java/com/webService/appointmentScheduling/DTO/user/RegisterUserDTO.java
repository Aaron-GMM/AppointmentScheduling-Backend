package com.webService.appointmentScheduling.DTO.user;

public record RegisterUserDTO(String nome, String login, String password, UserRole role) {
}
