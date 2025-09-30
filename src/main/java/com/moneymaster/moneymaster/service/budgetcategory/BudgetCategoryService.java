package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BudgetCategoryService {
    BudgetCategory createBudgetCategory(UUID budgetId, BudgetCategory budgetCategory);
    List<BudgetCategory> createDefaultBudgetCategories (UUID budgetId);
    List<BudgetCategory> getBudgetCategories(UserPrincipal currentUser);
    BudgetCategory getBudgetCategory(UUID budgetCategoryId);
    void deleteBudgetCategory(BudgetCategory budgetCategory);
    BudgetCategory updateBudgetCategory(UserPrincipal currentUser, UUID budgetCategoryId, BudgetCategoryDto budgetCategoryDto);
    List<BudgetCategory> updateBudgetCategoriesList (UUID userId, List<BudgetCategory> budgetCategoryList);
    BigDecimal sumFixedCosts(BudgetCategory budgetCategory);
    BigDecimal getTotal(BudgetCategory budgetCategory, BigDecimal monthlyIncome);
}
