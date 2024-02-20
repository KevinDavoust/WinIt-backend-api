package com.cda.winit.user.domain.service;

import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            String currentUsername = authentication.getName();
            Optional<com.cda.winit.user.domain.entity.User> currentUser = userRepository.findByEmail(currentUsername);

            if (currentUser.isPresent()) {
                return currentUser;
            } else {
                throw new UserPrincipalNotFoundException("User not found");
            }
        } else {
            throw new AccessDeniedException("User is not authenticated");
        }
    }

    public Long getCurrentUserId() throws Exception {
        Optional<User> currentUser = this.getCurrentUser();
        if (currentUser.isPresent()) {
            return currentUser.get().getId();
        } else {
            throw new UserPrincipalNotFoundException("User not found");
        }
    }
}
