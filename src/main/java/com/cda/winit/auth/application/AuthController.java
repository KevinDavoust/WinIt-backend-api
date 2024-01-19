package com.cda.winit.auth.application;

import com.cda.winit.auth.domain.dto.JwtResponse;
import com.cda.winit.auth.domain.entity.User;
import com.cda.winit.auth.domain.service.JwtTokenService;
import com.cda.winit.auth.domain.service.UserDetailsServiceImpl;
import com.cda.winit.auth.domain.service.UserLoginService;
import com.cda.winit.auth.domain.service.UserRegistrationService;
import com.cda.winit.auth.infrastructure.repository.exception.RegistrationErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private UserRegistrationService userRegistrationService;

    public AuthController(
            UserLoginService userLoginService,
            JwtTokenService jwtTokenService,
            UserDetailsServiceImpl userDetailsService,
            UserRegistrationService userRegistrationService
    ) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
    }

    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {
        try {
            userLoginService.login(userBody);
            String token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getEmail()));

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }*/
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User userBody) throws Exception {
        try {
            userLoginService.login(userBody);
            String token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getEmail()));

            JwtResponse jwtResponse = new JwtResponse(token);

            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userBody) throws RegistrationErrorException {
        try {
            return ResponseEntity.status(201).body(userRegistrationService.UserRegistration(userBody));
        } catch (Exception e) {
            throw new RegistrationErrorException(e.getMessage());
        }
    }
}
