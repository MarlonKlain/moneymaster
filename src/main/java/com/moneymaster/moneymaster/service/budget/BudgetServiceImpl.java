package com.moneymaster.moneymaster.service.budget;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budget.BudgetDto;
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
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService{

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final BudgetCategoryService budgetCategoryService;
    private final BudgetMapper budgetMapper;

    public BudgetServiceImpl(BudgetRepository budgetRepository, UserRepository userRepository, BudgetCategoryService budgetCategoryService, BudgetMapper budgetMapper){
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.budgetCategoryService = budgetCategoryService;
        this.budgetMapper = budgetMapper;
    }


    @Override
    @Transactional
    public Budget createBudget(UserPrincipal currentUser, Budget budget) {
        if(currentUser.getId() == null){
            throw new IllegalArgumentException("An User ID must be provided!");
        }

        if(budget.getBudgetId() != null){
            throw new IllegalArgumentException("A Budget ID must no be provided by the client!");
        }

        if(budget.getMonthlyIncome() == null) {
            throw new IllegalArgumentException("The monthly income must be provided!");
        }

        User user = userRepository.getReferenceById(currentUser.getId());
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
    public Optional<Budget> getBudgetByUser(UserPrincipal currentUser) {
        return budgetRepository.findByUser_UserId(currentUser.getId());
    }

    @Override
    @Transactional
    public void deleteBudget(UserPrincipal currentUser) {
        budgetRepository.deleteByUser_UserIdAndBudgetId(currentUser.getId(), currentUser.getBudgetId());
    }

    @Override
    public Budget updateBudget(UserPrincipal currentUser, BudgetDto budget) {

        Budget budgetByUser = budgetMapper.fromDto(budget);
        if(budgetByUser.getBudgetId() == null){
            throw new IllegalArgumentException("A Budget ID must no be provided by the client!");
        }

        if(!currentUser.getBudgetId().equals(budgetByUser.getBudgetId())){
            throw new IllegalArgumentException("Something went wrong. Please, provide the correct budget");
        }

        Budget budgetToUpdate = budgetRepository.findById(currentUser.getBudgetId()).orElseThrow(() -> new IllegalArgumentException("Budget not found"));

        budgetToUpdate.setMonthlyIncome(budgetByUser.getMonthlyIncome());

        return budgetRepository.save(budgetToUpdate);
    }

}
