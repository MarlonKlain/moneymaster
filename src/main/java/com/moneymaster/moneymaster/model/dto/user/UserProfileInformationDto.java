package com.moneymaster.moneymaster.model.dto.user;

public record UserProfileInformationDto(
        String firstName,
        String lastName,
        String email,
        String username
) {
}
