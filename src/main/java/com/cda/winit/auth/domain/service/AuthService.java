package com.cda.winit.auth.domain.service;

import com.cda.winit.auth.domain.dto.AuthRequest;
import com.cda.winit.auth.domain.dto.AuthResponse;
import com.cda.winit.auth.domain.dto.RegisterRequest;
import com.cda.winit.auth.domain.entity.User;
import com.cda.winit.auth.domain.service.util.JwtService;
import com.cda.winit.auth.infrastructure.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public Map<String, String> register(RegisterRequest request, HttpServletRequest httpRequest) throws Exception {

        if (!repository.findByEmail(request.getEmail()).isPresent()) {
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRequiredRole())
                    .createdAt(request.getCreatedAt())
                    .isEnabled(Boolean.valueOf(request.getEnabled()))
                    .build();

            repository.save(user);

            Map<String, String> body = new HashMap<>();
            body.put("message", "Account successfully created");
            return body;

        } else {
            httpRequest.setAttribute("username_taken_exception", "Username already taken");
            throw new Exception("Username already taken");
        }
    }

    public AuthResponse authenticate(AuthRequest request, HttpServletRequest httpRequest) {

        /* Permet de comparer le pwd reçu de la request reçue avec le pwd haché de la BDD.
         * La méthode authenticate() permet surtout de garantir que les informations d'identification sont exactes
         * Permet de transmettre au contexte de Spring l'utilisateur qui a été trouvé.
         *  Cela permet de l'utiliser pour autoriser/refuser l'accès aux ressources protégées
         * S'il n'est pas trouvé, une erreur est levée et la méthode s'arrête.
         */
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            /* Si tout va bien et que les informations sont OK, on peut récupérer l'utilisateur */
            User user = repository.findByEmail(request.getEmail()).orElseThrow();

            /* On extrait le rôle de l'utilisateur */
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", user.getRole().toString());

            /* On génère le token avec le rôle */
            String jwtToken = jwtService.generateToken(new HashMap<>(extraClaims), user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .message("Logged In")
                    .build();

        } catch (BadCredentialsException ex) {
            httpRequest.setAttribute("bad_credentials", ex.getMessage());
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
