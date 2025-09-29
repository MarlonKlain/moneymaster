package com.moneymaster.moneymaster.service.fixedcost;

import com.moneymaster.moneymaster.model.dto.fixedcost.FixedCostDto;
import com.moneymaster.moneymaster.model.entity.FixedCost;

import java.util.List;
import java.util.UUID;

public interface FixedCostService {
    List<FixedCost> createFixedCost(UUID currentUserId, List<FixedCostDto> fixedCostDtoListDto);
    List<FixedCost> getFixedCosts(UUID budgetCategoryId);
    void deleteFixedCost(FixedCostDto fixedCostDto);
    FixedCost updateFixedCost (UUID fixedCostId, FixedCost fixedCost);

}
