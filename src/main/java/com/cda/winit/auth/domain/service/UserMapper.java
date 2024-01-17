package com.cda.winit.auth.domain.service;

import com.cda.winit.auth.domain.dto.UserDto;
import com.cda.winit.auth.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto transformUserEntityInUserDto(User user, Boolean isForResponse) {
        UserDto userDTO = new UserDto();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEnabled(true);
        userDTO.setRoles(user.getRoles());
        userDTO.setPassword(isForResponse ? "" : user.getPassword());

        return userDTO;
    }
}
