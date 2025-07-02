package com.moneymaster.moneymaster.model.dto;

import com.moneymaster.moneymaster.model.entity.BudgetCategory;

import java.math.BigDecimal;
import java.util.UUID;

public record FixedCostDto(
        UUID fixedCostId,
        BigDecimal amount,
        String description
) {
}
