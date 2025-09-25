package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.repository.BudgetCategoryRepository;
import com.moneymaster.moneymaster.repository.BudgetRepository;
import com.moneymaster.moneymaster.repository.UserRepository;
import com.moneymaster.moneymaster.service.fixedcost.FixedCostService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BudgetCategoryServiceImpl implements BudgetCategoryService{

    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final FixedCostService fixedCostService;

    public BudgetCategoryServiceImpl(BudgetCategoryRepository budgetCategoryRepository, BudgetRepository budgetRepository, UserRepository userRepository, FixedCostService fixedCostService){
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.fixedCostService = fixedCostService;
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
                new BudgetCategory(null, budget, new BigDecimal("0.5"), "Needs", null, null),
                new BudgetCategory(null, budget, new BigDecimal("0.3"), "Wants", null, null),
                new BudgetCategory(null, budget, new BigDecimal("0.2"), "Savings", null, null)
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
    public BudgetCategory getBudgetCategory(UUID budgetCategoryId) {
        return budgetCategoryRepository.findById(budgetCategoryId).orElseThrow(() -> new IllegalArgumentException("Budget Category not Found!"));
    }


    @Override
    @Transactional
    public void deleteBudgetCategory (BudgetCategory budgetCategory) {
        if(budgetCategory != null){
        budgetCategoryRepository.deleteById(budgetCategory.getBudgetCategoryId());
        }
    }

    @Override
    @Transactional
    //resolver essa gambiarra
    public BudgetCategory updateBudgetCategory(UUID userId, UUID budgetCategoryId, BudgetCategory budgetCategory) {

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
        List<FixedCost> fixedCostList = budgetCategory.getFixedCosts();
        fixedCostList.forEach(fixedCost -> {
            fixedCost.setBudgetCategory(budgetCategoryToUpdate);
        });

        fixedCostService.createFixedCost(userId, fixedCostList);

        return budgetCategoryRepository.save(budgetCategoryToUpdate);
    }

    @Override
    @Transactional
    public List<BudgetCategory> updateBudgetCategoriesList(UUID userId, List<BudgetCategory> budgetCategoryList) {

        if(budgetCategoryList.isEmpty()){
            throw new IllegalArgumentException("At least one budget category most be provided!");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        budgetCategoryList.forEach(budgetCategory -> {
                    budgetCategory.setBudget(user.getBudget());
                }
        );

        user.setHasSetBudgetCategories(true);
        userRepository.save(user);
        return budgetCategoryRepository.saveAll(budgetCategoryList);
    }

    @Override
    public BigDecimal sumFixedCosts(BudgetCategory budgetCategory) {
        return budgetCategory.getFixedCosts().stream()
                .map(FixedCost::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotal(BudgetCategory budgetCategory, BigDecimal monthlyIncome) {
        return monthlyIncome.multiply(budgetCategory.getPercentage());
    }


}
