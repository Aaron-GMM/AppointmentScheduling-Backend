package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Long> {
}
