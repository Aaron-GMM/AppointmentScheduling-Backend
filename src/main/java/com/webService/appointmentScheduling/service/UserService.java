package com.webService.appointmentScheduling.service;
import com.webService.appointmentScheduling.repositories.UserRepository;
import com.webService.appointmentScheduling.DTO.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User insert(User user){
        return this.repository.save(user);
    }
}
