package com.webService.appointmentScheduling.repositories;

import com.webService.appointmentScheduling.entities.Queries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface queriesRepository extends JpaRepository<Queries, Long> {
}
