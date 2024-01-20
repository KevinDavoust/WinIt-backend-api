package com.cda.winit.auth.application;

import com.cda.winit.auth.domain.dto.AuthRequest;
import com.cda.winit.auth.domain.dto.AuthResponse;
import com.cda.winit.auth.domain.dto.RegisterRequest;
import com.cda.winit.auth.domain.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) throws Exception {
        return ResponseEntity.ok(service.register(request, httpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        System.out.println(request);
        AuthResponse authenticationResponse = service.authenticate(request, httpRequest);
        System.out.println(authenticationResponse);
        return ResponseEntity.ok(authenticationResponse); // Réponse réussie avec le token JWT
    }
}
