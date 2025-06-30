package com.moneymaster.moneymaster.repository;

import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    Optional<Budget> findByUser_UserId(UUID userId);
    void deleteByUser_UserIdAndBudgetId(UUID userId, UUID budgetId);
}
