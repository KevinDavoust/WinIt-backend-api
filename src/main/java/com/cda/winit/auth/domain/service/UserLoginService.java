package com.cda.winit.auth.domain.service;

import com.cda.winit.auth.domain.entity.User;
import com.cda.winit.auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    public UserLoginService(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    public User login(User user) {
        User userEntity = getUserEntityByUsername(user.getUsername());
        if (!verifyHashedPasswordDuringLogin(user.getPassword(), userEntity.getPassword())){
            throw new RuntimeException();
        }

        user.setRoles(userEntity.getRoles());

        return user;
    }

    public boolean verifyHashedPasswordDuringLogin(String password, String hashedPassword) {
        return bcryptPasswordEncoder.matches(password, hashedPassword);
    }

    public User getUserEntityByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}

