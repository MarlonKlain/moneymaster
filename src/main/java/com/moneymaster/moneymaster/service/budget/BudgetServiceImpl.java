package com.moneymaster.moneymaster.service.budget;

import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import com.moneymaster.moneymaster.repository.BudgetRepository;
import com.moneymaster.moneymaster.repository.UserRepository;
import com.moneymaster.moneymaster.service.budgetcategory.BudgetCategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService{

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final BudgetCategoryService budgetCategoryService;

    public BudgetServiceImpl(BudgetRepository budgetRepository, UserRepository userRepository, BudgetCategoryService budgetCategoryService){
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.budgetCategoryService = budgetCategoryService;
    }


    @Override
    @Transactional
    public Budget createBudget(UUID userId, Budget budget) {
        if(userId == null){
            throw new IllegalArgumentException("An User ID must be provided!");
        }

        if(budget.getBudgetId() != null){
            throw new IllegalArgumentException("A Budget ID must no be provided by the client!");
        }

        if(budget.getMonthlyIncome() == null) {
            throw new IllegalArgumentException("The monthly income must be provided!");
        }

        User user = userRepository.getReferenceById(userId);
        Budget savedBudget = budgetRepository.save(
                new Budget(
                        null,
                        user,
                        budget.getMonthlyIncome(),
                        null
                )
        );

        user.setHasSetMonthlyIncome(true);

        userRepository.save(user);

        List<BudgetCategory> budgetCategoryList = budgetCategoryService.createDefaultBudgetCategories(savedBudget.getBudgetId());

        savedBudget.setBudgetCategories(budgetCategoryList);

        return savedBudget;

    }

    @Override
    public Optional<Budget> getBudgetByUser(UUID userId) {
        return budgetRepository.findByUser_UserId(userId);
    }

    @Override
    @Transactional
    public void deleteBudget(UUID userId, UUID budgetId) {
        budgetRepository.deleteByUser_UserIdAndBudgetId(userId, budgetId);
    }

    @Override
    public Budget updateBudget(UUID budgetId, Budget budget) {
        if(budgetId == null){
            throw new IllegalArgumentException("A Budget ID must no be provided by the client!");
        }

        Budget budgetToUpdate = budgetRepository.findById(budgetId).orElseThrow(() -> new IllegalArgumentException("Budget not found"));

        budgetToUpdate.setMonthlyIncome(budget.getMonthlyIncome());

        return budgetRepository.save(budgetToUpdate);
    }

}
