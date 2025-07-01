package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.repository.BudgetCategoryRepository;
import com.moneymaster.moneymaster.repository.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BudgetCategoryServiceImpl implements BudgetCategoryService{

    private final BudgetCategoryRepository budgetCategoryRepository;
    private  final BudgetRepository budgetRepository;

    public BudgetCategoryServiceImpl(BudgetCategoryRepository budgetCategoryRepository, BudgetRepository budgetRepository){
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public BudgetCategory createBudgetCategory(UUID budgetId, BudgetCategory budgetCategory) {
        if(budgetCategory.getBudgetCategoryId() != null){
            throw new IllegalArgumentException("ID must not be provided by the client!");
        }

        if(budgetId == null){
            throw new IllegalArgumentException("The budget ID must be provided!.");
        }

        if(budgetCategory.getPercentage() == null){
            throw new IllegalArgumentException("Please, provide the percentage that you want to save for this budget category.");
        }

        if(budgetCategory.getName() == null){
            throw new IllegalArgumentException("Please, provide the budget category's name.");
        }

       Budget budget = budgetRepository.getReferenceById(budgetId);

        return budgetCategoryRepository.save(
                new BudgetCategory(
                        null,
                        budget,
                        budgetCategory.getPercentage(),
                        budgetCategory.getName(),
                        budgetCategory.getImageUrl(),
                        null
                )
        );
    }

    @Override
    public List<BudgetCategory> getBudgetCategories(UUID budgetId) {
        if(budgetId == null){
            throw new IllegalArgumentException("A Budget ID must be provided!");
        }
        return budgetCategoryRepository.findByBudget_BudgetId(budgetId);

    }

    @Override
    @Transactional
    public void deleteBudgetCategory(UUID budgetId, UUID budgetCategoryId) {
        if(budgetId == null || budgetCategoryId == null){
            throw new IllegalArgumentException("A Budget ID or A Budget Category ID must be provided!");
        }
        budgetCategoryRepository.deleteByBudget_BudgetIdAndBudgetCategoryId(budgetId, budgetCategoryId);
    }
}
