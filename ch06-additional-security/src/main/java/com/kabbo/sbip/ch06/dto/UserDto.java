package com.kabbo.sbip.ch06.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        @NotEmpty(message = "Enter your firstname")
        String username,
        @NotEmpty(message = "Enter an email")
        @Email(message = "Email is not valid")
        String email,

        @NotEmpty(message = "Enter a password")
        String password,

        @NotEmpty(message = "Confirm your password")
        String confirmPassword
) {}
