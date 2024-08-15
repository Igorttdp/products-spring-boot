package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDto(@NotBlank String username, @NotBlank String password, @NotNull Boolean isAdmin) {
}
