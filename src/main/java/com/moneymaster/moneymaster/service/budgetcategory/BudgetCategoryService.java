package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BudgetCategoryService {
    BudgetCategoryDto createBudgetCategory(UserPrincipal currentUser, BudgetCategoryDto budgetCategoryDto);
    List<BudgetCategory> createDefaultBudgetCategories (UUID budgetId);
    List<BudgetCategory> getBudgetCategories(UserPrincipal currentUser);
    BudgetCategoryDto getBudgetCategory(UserPrincipal currentUser, UUID budgetCategoryId);
    void deleteBudgetCategory(BudgetCategory budgetCategory);
    BudgetCategoryDto updateBudgetCategory(UserPrincipal currentUser, UUID budgetCategoryId, BudgetCategoryDto budgetCategoryDto);
    List<BudgetCategory> updateBudgetCategoriesList (UserPrincipal currentUser, List<BudgetCategory> budgetCategoryList);
    BigDecimal sumFixedCosts(BudgetCategory budgetCategory);
    BigDecimal getTotal(BudgetCategory budgetCategory, BigDecimal monthlyIncome);
    BudgetCategoryDto createBudgetCategoryDto(BudgetCategory budgetCategory, Budget budget);
}
