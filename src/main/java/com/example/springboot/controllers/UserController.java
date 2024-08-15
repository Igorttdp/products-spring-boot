package com.example.springboot.controllers;

import com.example.springboot.dtos.LoginResponseDto;
import com.example.springboot.dtos.UserDto;
import com.example.springboot.services.UserService;
import com.example.springboot.util.ApiError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserDto userDto) {
        var result = userService.registerUser(userDto);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(HttpStatus.CONFLICT, "Username already used."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public LoginResponseDto login(Authentication authentication) {
        return new LoginResponseDto(userService.login(authentication));
    }


}
