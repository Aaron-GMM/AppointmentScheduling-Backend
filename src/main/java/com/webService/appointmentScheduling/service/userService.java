package com.webService.appointmentScheduling.service;
import com.webService.appointmentScheduling.repositories.userRepository;
import com.webService.appointmentScheduling.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {
    @Autowired
    private userRepository repository;

    public User insert(User user){
        return this.repository.save(user);
    }
}
