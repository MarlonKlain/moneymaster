package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budgetcategory.BudgetCategoryMapper;
import com.moneymaster.moneymaster.repository.BudgetCategoryRepository;
import com.moneymaster.moneymaster.repository.BudgetRepository;
import com.moneymaster.moneymaster.repository.UserRepository;
import com.moneymaster.moneymaster.service.fixedcost.FixedCostService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BudgetCategoryServiceImpl implements BudgetCategoryService{

    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final FixedCostService fixedCostService;
    private final BudgetCategoryMapper budgetCategoryMapper;

    public BudgetCategoryServiceImpl(BudgetCategoryRepository budgetCategoryRepository, BudgetRepository budgetRepository, UserRepository userRepository, FixedCostService fixedCostService, BudgetCategoryMapper budgetCategoryMapper){
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.fixedCostService = fixedCostService;
        this.budgetCategoryMapper = budgetCategoryMapper;
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
    public List<BudgetCategory> getBudgetCategories(UserPrincipal currentUser) {
        if(currentUser.getBudgetId() == null){
            throw new IllegalArgumentException("A Budget ID must be provided!");
        }
        return budgetCategoryRepository.findByBudget_BudgetId(currentUser.getBudgetId());

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
    public BudgetCategory updateBudgetCategory(UserPrincipal currentUser, UUID budgetCategoryId, BudgetCategoryDto budgetCategoryDto) {


        if(currentUser.getId() == null) {
            throw new IllegalArgumentException("Please, provide the User ID.");
        }

        BudgetCategory budgetCategoryUpdatedByUser = budgetCategoryMapper.fromDto(budgetCategoryDto);
        BudgetCategory budgetCategoryToUpdate = budgetCategoryRepository.findById(budgetCategoryId).orElseThrow(()-> new IllegalArgumentException("Budget category not found."));

        if(!budgetCategoryToUpdate.getBudgetCategoryId().equals(budgetCategoryUpdatedByUser.getBudgetCategoryId())){
            throw new IllegalArgumentException("Please, you are trying to update different budget categories at the same time");
        }

        if(budgetCategoryUpdatedByUser.getName() != null){
            budgetCategoryToUpdate.setName(budgetCategoryUpdatedByUser.getName());
        }

        if(budgetCategoryUpdatedByUser.getPercentage() != null){
            budgetCategoryToUpdate.setPercentage(budgetCategoryUpdatedByUser.getPercentage());
        }

        if(budgetCategoryUpdatedByUser.getImageUrl() != null){
            budgetCategoryToUpdate.setImageUrl(budgetCategoryUpdatedByUser.getImageUrl());
        }

        //saving the list of fixed costs
        List<FixedCost> fixedCostsToUpdate = budgetCategoryToUpdate.getFixedCosts();
        //clearing all the previous fixed costs
        fixedCostsToUpdate.clear();
        //setting the reference to the budgetCategory in the fixed costs provided by the user
        budgetCategoryUpdatedByUser.getFixedCosts().forEach(fixedCost -> {
            fixedCost.setBudgetCategory(budgetCategoryToUpdate);
            fixedCostsToUpdate.add(fixedCost);
        });

        //setting the new fixed costs in the object that will be used to update
        budgetCategoryToUpdate.setFixedCosts(fixedCostsToUpdate);

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


}
