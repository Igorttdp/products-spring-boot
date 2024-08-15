package com.example.springboot.dtos;

import com.example.springboot.models.RoleModel;

import java.util.List;

public record UserResponseDto(String username, List<RoleModel> roles) {
}
