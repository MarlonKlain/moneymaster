package com.moneymaster.moneymaster.model.mappers.fixedcost;

import com.moneymaster.moneymaster.model.dto.FixedCostDto;
import com.moneymaster.moneymaster.model.entity.FixedCost;

public interface FixedCostMapper {
    FixedCost fromDto(FixedCostDto fixedCostDto);
    FixedCostDto toDto(FixedCost fixedCost);
}
