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
                        .frameOptions(frameOptions -> frameOptions.disable())) // Desativa opções de frame (se necessário)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Permite acesso público ao login
                        .requestMatchers(HttpMethod.GET, "/doctor/all").permitAll() // Permite acesso público ao "find all doctors"
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // Permite acesso autenticado para as rotas de detalhe e busca por CPF

                        .requestMatchers(HttpMethod.GET, "/doctor/id/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/doctor/cpf/").authenticated()

                        // Exige role ADMIN para as operações de criação, atualização e deleção
                        .requestMatchers(HttpMethod.POST, "/doctor/register").hasRole("ADMIN") // Apenas ADMIN pode criar
                        .requestMatchers(HttpMethod.PUT, "/doctor/id/{id}").hasRole("ADMIN") // Apenas ADMIN pode atualizar
                        .requestMatchers(HttpMethod.DELETE, "/doctor/id/{id}").hasRole("ADMIN") // Apenas ADMIN pode deletar

                        // Exige autenticação para o endpoint do Appointment
                        .requestMatchers(HttpMethod.GET, "/appointment").authenticated()

                        // Outras requisições podem ser permitidas ou protegidas conforme necessário
                        .anyRequest().permitAll() // Outros endpoints exigem permissões conforme necessário
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Filtro de autenticação JWT
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
