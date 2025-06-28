package com.moneymaster.moneymaster.model.mappers.budget;

import com.moneymaster.moneymaster.model.dto.BudgetDto;
import com.moneymaster.moneymaster.model.entity.Budget;

public interface BudgetMapper {
    Budget fromDto(BudgetDto budgetDto);
    BudgetDto toDto(Budget budget);
}
