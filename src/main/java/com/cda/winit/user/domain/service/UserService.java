package com.cda.winit.user.domain.service;

import com.cda.winit.user.domain.dto.UserDto;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.domain.service.mapper.UserMapper;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

    public List<UserDto> getAllUsers() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            List<User> users = userRepository.findAll();

            if (users != null && !users.isEmpty()) {
                return users.stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList());
            } else {
                throw new NoSuchElementException("No users found");
            }
        } else {
            throw new AccessDeniedException("User is not authenticated");
        }
    }
}
