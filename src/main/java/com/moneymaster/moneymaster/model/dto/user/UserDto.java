package com.moneymaster.moneymaster.model.dto.user;

import com.moneymaster.moneymaster.model.entity.Budget;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String password,
        String username,
        String token,
        boolean hasCompletedOnboarding,
        boolean hasSetMonthlyIncome,
        boolean hasSetBudgetCategories,
        boolean hasSetFixedCosts

) {

}
