package com.moneymaster.moneymaster.model.dto;

import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDto(
        BigDecimal totalIncome,
        BigDecimal totalFixedCosts,
        BigDecimal flexibleSpending,
        List<BudgetCategoryDto> budgetCategories
) {
}
