package com.moneymaster.moneymaster.model.dto.user;

public record UserLoginResponseDto(
        String jwtToken,
        UserOnboardingStatusDto onboarding
) {
}
