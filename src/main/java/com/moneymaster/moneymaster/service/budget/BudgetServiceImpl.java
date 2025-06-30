package com.moneymaster.moneymaster.service.budget;

import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import com.moneymaster.moneymaster.repository.BudgetRepository;
import com.moneymaster.moneymaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService{

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository, UserRepository userRepository){
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Budget createBudget(UUID userId, Budget budget) {
        if(userId == null){
            throw new IllegalArgumentException("An User ID must be provided!");
        }

        if(budget.getBudgetId() != null){
            throw new IllegalArgumentException("A Budget ID must no be provided by the client!");
        }

        if(budget.getMonthlyIncome() == null){
            throw new IllegalArgumentException("The monthly income must be provided!");
        }


        User user = userRepository.getReferenceById(userId);

        return budgetRepository.save(
                new Budget(
                        null,
                        user,
                        budget.getMonthlyIncome(),
                        null
                )
        );
    }

    @Override
    public Optional<Budget> getBudget(UUID userId) {
        System.out.println("userId = " + userId);
        return budgetRepository.findByUser_UserId(userId);
    }

    @Override
    @Transactional
    public void deleteBudget(UUID userId, UUID budgetId) {
        budgetRepository.deleteByUser_UserIdAndBudgetId(userId, budgetId);
    }
}
