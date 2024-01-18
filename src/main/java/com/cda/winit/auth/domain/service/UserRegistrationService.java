package com.cda.winit.auth.domain.service;

import com.cda.winit.auth.domain.dto.UserDto;
import com.cda.winit.auth.domain.entity.User;
import com.cda.winit.auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private UserMapper userMapper;

    public UserRegistrationService(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto UserRegistration(User userData) throws Exception {
        userData.setPassword(GenerateHashedPassword(userData.getPassword()));
        try {
            return userMapper.transformUserEntityInUserDto(userRepository.save(userData), true);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String GenerateHashedPassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }
}

