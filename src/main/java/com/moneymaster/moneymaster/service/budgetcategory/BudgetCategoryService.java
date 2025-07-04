package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.entity.BudgetCategory;

import java.util.List;
import java.util.UUID;

public interface BudgetCategoryService {
    BudgetCategory createBudgetCategory(UUID budgetId, BudgetCategory budgetCategory);
    List<BudgetCategory> getBudgetCategories(UUID budgetId);
    void deleteBudgetCategory(UUID budgetId, UUID budgetCategoryId);
    BudgetCategory updateBudgetCategory(UUID budgetCategoryId, BudgetCategory budgetCategory);
}
