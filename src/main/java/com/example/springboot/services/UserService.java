package com.example.springboot.services;

import com.example.springboot.configs.security.JwtService;
import com.example.springboot.dtos.UserDto;
import com.example.springboot.dtos.UserResponseDto;
import com.example.springboot.models.UserModel;
import com.example.springboot.repositories.RolesRepository;
import com.example.springboot.repositories.UserRepository;
import com.example.springboot.services.impl.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    JwtService jwtService;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserResponseDto registerUser(UserDto userDto) {
        Optional<UserModel> existUser = userRepository.findByUsername(userDto.username());

        if (existUser.isPresent()) {
            return null;
        }

        UserModel user = new UserModel();
        BeanUtils.copyProperties(userDto, user);

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.assignRole(rolesRepository.findByRoleUser());

        if (userDto.isAdmin()) {
            user.assignRole(rolesRepository.findByRoleAdmin());
        }

        userRepository.save(user);

        return new UserResponseDto(user.getUsername(), user.getRoles().stream().toList());
    }

    @Override
    public String login(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
