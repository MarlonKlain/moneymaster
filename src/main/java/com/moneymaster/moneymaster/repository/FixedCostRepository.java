package com.moneymaster.moneymaster.repository;

import com.moneymaster.moneymaster.model.entity.FixedCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FixedCostRepository extends JpaRepository<FixedCost, UUID> {
    List<FixedCost> findByBudgetCategory_BudgetCategoryIdAndFixedCostId(UUID budgetCategoryId, UUID fixedCostId);
    void deleteByBudgetCategory_BudgetCategoryIdAndFixedCostId(UUID budgetCategoryId, UUID fixedCostId);
}
