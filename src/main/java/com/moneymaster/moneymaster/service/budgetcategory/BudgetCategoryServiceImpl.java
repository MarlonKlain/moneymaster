package com.moneymaster.moneymaster.service.budgetcategory;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budgetcategory.BudgetCategoryMapper;
import com.moneymaster.moneymaster.model.mappers.fixedcost.FixedCostMapper;
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
    private final FixedCostMapper fixedCostMapper;

    public BudgetCategoryServiceImpl(BudgetCategoryRepository budgetCategoryRepository, BudgetRepository budgetRepository, UserRepository userRepository, FixedCostService fixedCostService, BudgetCategoryMapper budgetCategoryMapper, FixedCostMapper fixedCostMapper){
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.fixedCostService = fixedCostService;
        this.budgetCategoryMapper = budgetCategoryMapper;
        this.fixedCostMapper = fixedCostMapper;
    }

    @Override
    public BudgetCategoryDto createBudgetCategory(UserPrincipal currentUser, BudgetCategoryDto budgetCategoryDto) {
        BudgetCategory budgetCategoryToCreate = budgetCategoryMapper.fromDto(budgetCategoryDto);

        if(budgetCategoryToCreate.getBudgetCategoryId() != null){
            throw new IllegalArgumentException("ID must not be provided by the client!");
        }

        if(currentUser.getBudgetId() == null){
            throw new IllegalArgumentException("The budget ID must be provided!.");
        }

        if(budgetCategoryToCreate.getPercentage() == null){
            throw new IllegalArgumentException("Please, provide the percentage that you want to save for this budget category.");
        }

        if(budgetCategoryToCreate.getName() == null){
            throw new IllegalArgumentException("Please, provide the budget category's name.");
        }


        //immutable bug here
        Budget budget = budgetRepository.getReferenceById(currentUser.getBudgetId());
        budgetCategoryToCreate.setBudget(budget);

        List<FixedCost> fixedCostImmutable = budgetCategoryToCreate.getFixedCosts();
        List<FixedCost> fixedCostToCreate = new ArrayList<>();

        fixedCostImmutable.forEach(fixedCost -> {
            fixedCost.setBudgetCategory(budgetCategoryToCreate);
            fixedCostToCreate.add(fixedCost);
        });

        budgetCategoryToCreate.setFixedCosts(fixedCostToCreate);

        BudgetCategory budgetCategoryUpdated = budgetCategoryRepository.save(budgetCategoryToCreate);
        return createBudgetCategoryDto(budgetCategoryUpdated, budget);
    }



    @Override
    public List<BudgetCategory> getBudgetCategories(UserPrincipal currentUser) {
        if(currentUser.getBudgetId() == null){
            throw new IllegalArgumentException("A Budget ID must be provided!");
        }
        return budgetCategoryRepository.findByBudget_BudgetId(currentUser.getBudgetId());

    }

    @Override
    public BudgetCategoryDto getBudgetCategory(UserPrincipal currentUser, UUID budgetCategoryId) {
        if(currentUser.getBudgetId() == null || budgetCategoryId == null) {
            throw new IllegalArgumentException("Please, provided the corrects IDS");
        }

        BudgetCategory budgetCategoryToDto = budgetCategoryRepository.findById(budgetCategoryId).orElseThrow(()-> new IllegalArgumentException("Budget category not found."));

        if(!currentUser.getBudgetId().equals(budgetCategoryToDto.getBudget().getBudgetId())){
            throw new IllegalArgumentException("Something went wrong, please provide the correct budget category");
        }


        return createBudgetCategoryDto(budgetCategoryToDto, budgetCategoryToDto.getBudget());
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
    public BudgetCategoryDto updateBudgetCategory(UserPrincipal currentUser, UUID budgetCategoryId, BudgetCategoryDto budgetCategoryDto) {


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
        BudgetCategory budgetCategoryUpdated = budgetCategoryRepository.save(budgetCategoryToUpdate);

        return this.createBudgetCategoryDto(budgetCategoryUpdated, budgetCategoryUpdated.getBudget());
    }

    @Override
    @Transactional
    public List<BudgetCategory> updateBudgetCategoriesList(UserPrincipal currentUser, List<BudgetCategory> budgetCategoryList) {

        if(budgetCategoryList.isEmpty()){
            throw new IllegalArgumentException("At least one budget category most be provided!");
        }

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new IllegalArgumentException("User not found!"));
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
    public BudgetCategoryDto createBudgetCategoryDto(BudgetCategory budgetCategory, Budget budget) {
        BigDecimal total = this.getTotal(budgetCategory, budget.getMonthlyIncome());
        BigDecimal totalFixedCosts = this.sumFixedCosts(budgetCategory);
        BigDecimal flexibleSpending = total.subtract(totalFixedCosts);
        return new BudgetCategoryDto(
                budgetCategory.getBudgetCategoryId(),
                budgetCategory.getPercentage(),
                total,
                budgetCategory.getName(),
                budgetCategory.getImageUrl(),
                totalFixedCosts,
                flexibleSpending,
                budgetCategory.getFixedCosts().stream().map(fixedCostMapper::toDto).toList());
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
