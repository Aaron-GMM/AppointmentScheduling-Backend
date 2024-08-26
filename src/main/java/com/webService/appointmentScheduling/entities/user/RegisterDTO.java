package com.webService.appointmentScheduling.entities.user;

public record RegisterDTO(String nome,String login,String password, UserRole role) {
}
