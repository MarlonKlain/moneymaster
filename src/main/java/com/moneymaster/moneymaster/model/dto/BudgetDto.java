package com.moneymaster.moneymaster.model.dto;

import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BudgetDto(
        UUID budgetId,
        BigDecimal monthlyIncome,
        List<BudgetCategoryDto> budgetCategories
) {
}
