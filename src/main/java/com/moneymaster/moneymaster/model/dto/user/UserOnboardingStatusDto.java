package com.moneymaster.moneymaster.model.dto.user;

public record UserOnboardingStatusDto(
        boolean hasCompletedOnboarding,
        boolean hasSetMonthlyIncome,
        boolean hasSetBudgetCategories,
        boolean hasSetFixedCosts
) {
}
