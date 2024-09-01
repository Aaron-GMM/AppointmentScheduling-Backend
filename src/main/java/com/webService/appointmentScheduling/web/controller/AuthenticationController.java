package com.webService.appointmentScheduling.web.controller;
import com.webService.appointmentScheduling.DTO.user.LoginResponseDTO;
import com.webService.appointmentScheduling.DTO.user.User;
import com.webService.appointmentScheduling.infra.security.TokenService;
import com.webService.appointmentScheduling.repositories.userRepository;
import com.webService.appointmentScheduling.service.userService;
import com.webService.appointmentScheduling.DTO.user.AuthenticationDTO;
import com.webService.appointmentScheduling.DTO.user.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
     private userRepository userRepository;
    @Autowired
    private userService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok( new LoginResponseDTO(token));
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterUserDTO data){
        if (this.userRepository.findByLogin(data.login())!= null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.nome(),data.login(),encryptedPassword, data.role());

        this.userService.insert(newUser);
        return ResponseEntity.ok().build();
    }
}
