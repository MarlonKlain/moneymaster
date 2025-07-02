package com.moneymaster.moneymaster.service.fixedcost;

import com.moneymaster.moneymaster.model.entity.FixedCost;

import java.util.List;
import java.util.UUID;

public interface FixedCostService {
    FixedCost createFixedCost(UUID budgetCategoryId, FixedCost fixedCost);
    List<FixedCost> getFixedCosts(UUID budgetCategoryId);
    void deleteFixedCost(UUID budgetCategoryId, UUID fixedCostID);

}
