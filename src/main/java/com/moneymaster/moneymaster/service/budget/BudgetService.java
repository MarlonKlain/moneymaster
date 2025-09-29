package com.moneymaster.moneymaster.service.budget;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.entity.Budget;

import java.util.Optional;

public interface BudgetService {
    Budget createBudget(UserPrincipal currentUser, Budget budget);
    Optional<Budget> getBudgetByUser(UserPrincipal currentUser);
    void deleteBudget(UserPrincipal currentUser);
    Budget updateBudget(UserPrincipal currentUser, Budget budget);
}
