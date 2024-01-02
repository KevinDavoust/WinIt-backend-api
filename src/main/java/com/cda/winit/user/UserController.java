package com.cda.winit.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email, HttpServletRequest request)
            throws AccessDeniedException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if (username.equals(email) || role.equals("[ROLE_ADMIN]")) {
            return ResponseEntity.ok(userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("email " + email + " not found")));
        } else {
            request.setAttribute("access_denied", "You do not have suffisant rights to access to this resource");
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    @GetMapping("/all")
    public List<User> getAll(HttpServletRequest request) throws AccessDeniedException {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (role.equals("[ROLE_ADMIN]")) {
            return userRepository.findAll();
        } else {
            request.setAttribute("access_denied", "You do not have suffisant rights to access to this resource");
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");

        }
    }
}
