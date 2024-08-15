package com.example.springboot.services.impl;

import com.example.springboot.dtos.UserDto;
import com.example.springboot.dtos.UserResponseDto;
import org.springframework.security.core.Authentication;

public interface UserServiceImpl {
    UserResponseDto registerUser(UserDto userDto);
    String login(Authentication authentication);
}
