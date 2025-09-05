package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.entity.BudgetCategory;

import java.util.List;
import java.util.UUID;

public interface BudgetCategoryService {
    BudgetCategory createBudgetCategory(UUID budgetId, BudgetCategory budgetCategory);
    List<BudgetCategory> createDefaultBudgetCategories (UUID budgetId);
    List<BudgetCategory> getBudgetCategories(UUID budgetId);
    void deleteBudgetCategory(BudgetCategory budgetCategory);
    BudgetCategory updateBudgetCategory(UUID budgetCategoryId, BudgetCategory budgetCategory);
    List<BudgetCategory> updateBudgetCategoriesList (UUID userId, List<BudgetCategory> budgetCategoryList);
}
