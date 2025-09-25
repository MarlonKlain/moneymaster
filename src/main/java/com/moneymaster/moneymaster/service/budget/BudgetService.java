package com.moneymaster.moneymaster.service.budget;

import com.moneymaster.moneymaster.model.entity.Budget;

import java.util.Optional;
import java.util.UUID;

public interface BudgetService {
    Budget createBudget(UUID userId, Budget budget);
    Optional<Budget> getBudgetByUser(UUID userId);
    void deleteBudget(UUID userId, UUID budgetId);
    Budget updateBudget(UUID budgetId, Budget budget);
}
