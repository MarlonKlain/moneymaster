package com.moneymaster.moneymaster.model.dto.fixedcost;

import java.math.BigDecimal;
import java.util.UUID;

public record FixedCostDto(
        UUID fixedCostId,
        BigDecimal amount,
        String description
) {
}
