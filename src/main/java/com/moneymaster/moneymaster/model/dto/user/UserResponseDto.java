package com.moneymaster.moneymaster.model.dto.user;

import com.moneymaster.moneymaster.model.dto.budget.BudgetDto;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String username,
        String password,
        boolean hasCompletedOnboarding,
        String token,
        BudgetDto budgetDto
) {
}
