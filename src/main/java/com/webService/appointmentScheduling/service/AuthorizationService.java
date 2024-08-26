package com.webService.appointmentScheduling.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.webService.appointmentScheduling.repositories.userRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    userRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return repository.findByLogin(username);
    }
}
