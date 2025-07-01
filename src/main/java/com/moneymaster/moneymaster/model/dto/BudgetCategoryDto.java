package com.moneymaster.moneymaster.model.dto;

import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.FixedCost;

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
