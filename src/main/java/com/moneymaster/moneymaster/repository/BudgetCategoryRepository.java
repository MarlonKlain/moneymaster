package com.moneymaster.moneymaster.repository;

import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, UUID> {
}
