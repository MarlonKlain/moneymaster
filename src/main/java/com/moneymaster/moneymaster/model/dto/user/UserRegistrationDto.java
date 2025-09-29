package com.moneymaster.moneymaster.model.dto.user;

public record UserRegistrationDto(
        String firstName,
        String lastName,
        String email,
        String password,
        String username,
        String confirmPassword
) {
}
