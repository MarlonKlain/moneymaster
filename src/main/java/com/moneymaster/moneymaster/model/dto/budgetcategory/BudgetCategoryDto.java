package com.moneymaster.moneymaster.model.dto.budgetcategory;

import com.moneymaster.moneymaster.model.dto.fixedcost.FixedCostDto;

import java.util.List;
import java.util.UUID;

public record BudgetCategoryDto(
        UUID budgetCategoryId,
        Double percentage,
        String name,
        String imageUrl,
        List<FixedCostDto>fixedCosts
) {
}
