package com.moneymaster.moneymaster.model.mappers.budgetcategory;

import com.moneymaster.moneymaster.model.dto.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.mappers.fixedcost.FixedCostMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class BudgetCategoryMapperImpl implements BudgetCategoryMapper {

    private final FixedCostMapper fixedCostMapper;

    public BudgetCategoryMapperImpl(FixedCostMapper fixedCostMapper){
        this.fixedCostMapper = fixedCostMapper;
    }

    @Override
    public BudgetCategory fromDto(BudgetCategoryDto budgetCategoryDto) {
        return new BudgetCategory(
                budgetCategoryDto.budgetCategoryId(),
                null,
                budgetCategoryDto.percentage(),
                budgetCategoryDto.name(),
                budgetCategoryDto.imageUrl(),
                Optional.ofNullable(budgetCategoryDto.fixedCosts())
                        .map(fixedCosts -> fixedCosts
                                .stream()
                                .map(fixedCostMapper::fromDto)
                                .toList())
                        .orElse(Collections.emptyList())

        );
    }

    @Override
    public BudgetCategoryDto toDto(BudgetCategory budgetCategory) {
        return new BudgetCategoryDto(
                budgetCategory.getBudgetCategoryId(),
                budgetCategory.getPercentage(),
                budgetCategory.getName(),
                budgetCategory.getImageUrl(),
                Optional.ofNullable(budgetCategory.getFixedCosts())
                        .map(fixedCosts -> fixedCosts
                                .stream()
                                .map(fixedCostMapper::toDto)
                                .toList())
                        .orElse(Collections.emptyList())
        );
    }
}
