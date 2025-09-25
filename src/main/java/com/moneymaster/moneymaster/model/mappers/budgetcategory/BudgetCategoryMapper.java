package com.moneymaster.moneymaster.model.mappers.budgetcategory;

import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;

import java.math.BigDecimal;


public interface BudgetCategoryMapper {
    BudgetCategory fromDto(BudgetCategoryDto budgetCategoryDto);
    BudgetCategoryDto toDto(BudgetCategory budgetCategory, BigDecimal totalIncome);


}
