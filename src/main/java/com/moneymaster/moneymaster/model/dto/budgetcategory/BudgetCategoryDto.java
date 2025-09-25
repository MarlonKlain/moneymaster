package com.moneymaster.moneymaster.model.dto.budgetcategory;

import com.moneymaster.moneymaster.model.dto.fixedcost.FixedCostDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BudgetCategoryDto(
        UUID budgetCategoryId,
        BigDecimal percentage,
        BigDecimal total,
        String name,
        String imageUrl,
        BigDecimal totalFixedCost,
        BigDecimal flexibleSpending,
        List<FixedCostDto>fixedCosts
) {
}
