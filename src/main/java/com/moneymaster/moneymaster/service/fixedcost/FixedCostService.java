package com.moneymaster.moneymaster.service.fixedcost;

import com.moneymaster.moneymaster.model.entity.FixedCost;

import java.util.List;
import java.util.UUID;

public interface FixedCostService {
    List<FixedCost> createFixedCost(UUID currentUserId, List<FixedCost> fixedCostDtoList);
    List<FixedCost> getFixedCosts(UUID budgetCategoryId);
    void deleteFixedCost(UUID fixedCostID);
    FixedCost updateFixedCost (UUID fixedCostId, FixedCost fixedCost);

}
