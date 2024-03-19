package com.cda.winit.user.application;


import com.cda.winit.user.domain.dto.UpdateUserRequest;
import com.cda.winit.user.domain.dto.UserDto;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.domain.service.UserService;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email, HttpServletRequest request) throws AccessDeniedException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if (username.equals(email) || role.equals("[ROLE_ADMIN]")) {
            return ResponseEntity.ok(userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("email " + email + " not found"))
            );
        } else {
            request.setAttribute("access_denied", "You do not have suffisant rights to access to this resource");
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    @GetMapping
    public List<UserDto> getAll() throws Exception {
            return userService.getAllUsers();
    }

    @GetMapping("/myself")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userRepository.findByEmail(currentUsername);
        return currentUser
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> edit(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) throws Exception {
        Optional<User> currentUser = userService.getCurrentUser();
        Long userId = userService.getCurrentUserId();

        if (currentUser.isPresent() && userId.equals(id)) {
            User updatedUser = currentUser.get();
            updatedUser.setFirstName(updateUserRequest.getFirstName());
            updatedUser.setLastName(updateUserRequest.getLastName());
            updatedUser.setEmail(updateUserRequest.getEmail());
            updatedUser.setCity(updateUserRequest.getCity());
            updatedUser.setUpdatedAt(LocalDateTime.now());

            userRepository.save(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}

