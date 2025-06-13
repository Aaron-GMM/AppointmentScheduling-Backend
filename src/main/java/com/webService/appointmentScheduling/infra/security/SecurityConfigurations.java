package com.webService.appointmentScheduling.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Desativa o CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configuração stateless
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()))
                .authorizeHttpRequests(authorize -> authorize

                        //User
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")

                        //Doctor
                        .requestMatchers(HttpMethod.GET, "/doctor/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/doctor/id/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/doctor/cpf/").authenticated()
                        .requestMatchers(HttpMethod.POST, "/doctor/register").hasRole("ADMIN") // Apenas ADMIN pode criar
                        .requestMatchers(HttpMethod.PUT, "/doctor/id/{id}").hasRole("ADMIN") // Apenas ADMIN pode atualizar
                        .requestMatchers(HttpMethod.DELETE, "/doctor/id/{id}").hasRole("ADMIN") // Apenas ADMIN pode deletar

                        //Patients
                        .requestMatchers(HttpMethod.POST,"/patients/register").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/patients/id/").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/patients/id/").authenticated()
                        .requestMatchers(HttpMethod.GET,"/patients/all").authenticated()
                        .requestMatchers(HttpMethod.GET,"/patients/id/").authenticated()
                        .requestMatchers(HttpMethod.POST,"/patients/cpf").authenticated()
                        .requestMatchers(HttpMethod.POST,"/patients/name").authenticated()




                        .requestMatchers(HttpMethod.GET, "/appointment").authenticated()

                        .anyRequest().permitAll()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
