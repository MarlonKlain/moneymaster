package com.moneymaster.moneymaster.repository;

import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, UUID> {
    List<BudgetCategory> findByBudget_BudgetId(UUID budgetId);
    BudgetCategory findByBudgetCategoryId(UUID budgetCategoryId);
    void deleteByBudget_BudgetIdAndBudgetCategoryId(UUID budgetId, UUID budgetCategoryId);
}
