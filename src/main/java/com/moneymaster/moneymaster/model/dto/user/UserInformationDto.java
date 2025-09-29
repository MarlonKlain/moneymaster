package com.moneymaster.moneymaster.model.dto.user;

import com.moneymaster.moneymaster.model.dto.budget.BudgetDto;

public record UserInformationDto(
        String jwtToken,
        UserOnboardingStatusDto onboarding,
        BudgetDto budgetDto
) {
}
