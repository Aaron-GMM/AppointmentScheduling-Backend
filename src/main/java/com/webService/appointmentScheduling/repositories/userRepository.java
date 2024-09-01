package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.DTO.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface userRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
