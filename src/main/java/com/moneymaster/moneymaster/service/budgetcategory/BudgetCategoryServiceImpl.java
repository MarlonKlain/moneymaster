package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.repository.BudgetCategoryRepository;
import com.moneymaster.moneymaster.repository.BudgetRepository;
import com.moneymaster.moneymaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BudgetCategoryServiceImpl implements BudgetCategoryService{

    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetCategoryServiceImpl(BudgetCategoryRepository budgetCategoryRepository, BudgetRepository budgetRepository, UserRepository userRepository){
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
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
    @Transactional
    public List<BudgetCategory> createDefaultBudgetCategories(UUID budgetId) {
        if(budgetId == null) {
        throw new IllegalArgumentException("A Budget ID must be provided!");
        }

        Budget budget = budgetRepository.getReferenceById(budgetId);


        List<BudgetCategory> defaultBudgetCategories = List.of(
                new BudgetCategory(null, budget, 0.5, "Needs", null, null),
                new BudgetCategory(null, budget, 0.3, "Wants", null, null),
                new BudgetCategory(null, budget, 0.2, "Savings", null, null)
        );

        return budgetCategoryRepository.saveAll(defaultBudgetCategories);
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
    public void deleteBudgetCategory (BudgetCategory budgetCategory) {
        if(budgetCategory == null){
            throw new IllegalArgumentException("A Budget Category ID must be provided!");
        }
        budgetCategoryRepository.deleteById(budgetCategory.getBudgetCategoryId());
    }

    @Override
    public BudgetCategory updateBudgetCategory(UUID budgetCategoryId, BudgetCategory budgetCategory) {
        if(budgetCategoryId == null){
            throw new IllegalArgumentException("The budget category ID must be provided!.");
        }

        BudgetCategory budgetCategoryToUpdate = budgetCategoryRepository.findById(budgetCategoryId).orElseThrow(() -> new IllegalArgumentException("Budget Category not found."));

        if(budgetCategory.getPercentage() != null){
            budgetCategoryToUpdate.setPercentage(budgetCategory.getPercentage());
        }

        if(budgetCategory.getName() != null){
            budgetCategoryToUpdate.setName(budgetCategory.getName());
        }

        if(budgetCategory.getImageUrl() != null ){
            budgetCategoryToUpdate.setImageUrl(budgetCategory.getImageUrl());
        }

        return budgetCategoryRepository.save(budgetCategoryToUpdate);
    }

    @Override
    public List<BudgetCategory> updateBudgetCategoriesList(UUID userId, List<BudgetCategory> budgetCategoryList) {

        if(budgetCategoryList.isEmpty()){
            throw new IllegalArgumentException("At least one budget category most be provided!");
        }

        User currentUser = userRepository.getReferenceById(userId);
        budgetCategoryList.forEach(budgetCategory -> {
                    budgetCategory.setBudget(currentUser.getBudget());
                }
        );
        return budgetCategoryRepository.saveAll(budgetCategoryList);
    }
}
