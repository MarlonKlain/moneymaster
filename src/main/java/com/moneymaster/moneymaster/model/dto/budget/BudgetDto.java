package com.moneymaster.moneymaster.model.dto.budget;

import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BudgetDto(
        UUID budgetId,
        BigDecimal monthlyIncome,
        List<BudgetCategoryDto> budgetCategories
) {
}
