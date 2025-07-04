package com.moneymaster.moneymaster.model.mappers.budget;

import com.moneymaster.moneymaster.model.dto.budget.BudgetDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.mappers.budgetcategory.BudgetCategoryMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class BudgetMapperImpl implements BudgetMapper {

    private final BudgetCategoryMapper budgetCategoryMapper;

    public BudgetMapperImpl(BudgetCategoryMapper budgetCategoryMapper) {
        this.budgetCategoryMapper = budgetCategoryMapper;
    }


    @Override
    public Budget fromDto(BudgetDto budgetDto) {
        return new Budget(
                budgetDto.budgetId(),
                null,
                budgetDto.monthlyIncome(),
                Optional.ofNullable(budgetDto.budgetCategories())
                        .map(budgetCategories -> budgetCategories
                                .stream()
                                .map(budgetCategoryMapper::fromDto)
                                .toList())
                        .orElse(Collections.emptyList())
                );
    }

    @Override
    public BudgetDto toDto(Budget budget) {
        return new BudgetDto(
          budget.getBudgetId(),
          budget.getMonthlyIncome(),
                Optional.ofNullable(budget.getBudgetCategories())
                        .map(budgetCategories -> budgetCategories
                                .stream()
                                .map(budgetCategoryMapper::toDto)
                                .toList())
                        .orElse(Collections.emptyList())
        );
    }
}
