package com.moneymaster.moneymaster.service.dashboard;

import com.moneymaster.moneymaster.model.dto.DashboardDto;
import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.dto.fixedcost.FixedCostDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budgetcategory.BudgetCategoryMapper;
import com.moneymaster.moneymaster.repository.BudgetCategoryRepository;
import com.moneymaster.moneymaster.repository.BudgetRepository;
import com.moneymaster.moneymaster.repository.FixedCostRepository;
import com.moneymaster.moneymaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardServiceImpl implements DashboardService{

    private final UserRepository userRepository;
    private final BudgetCategoryMapper budgetCategoryMapper;

    public DashboardServiceImpl(UserRepository userRepository, BudgetRepository budgetRepository, BudgetCategoryRepository budgetCategoryRepository, FixedCostRepository fixedCostRepository, BudgetCategoryMapper budgetCategoryMapper) {
        this.userRepository = userRepository;
        this.budgetCategoryMapper = budgetCategoryMapper;
    }

    @Override
    @Transactional
    public DashboardDto getDashboardSummary(UUID userId) {
        //finding the user
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));
        //getting all the user's budget categories and transforming to dto
        List<BudgetCategoryDto> budgetCategoryDtoList = user.getBudget().getBudgetCategories().stream().map(budgetCategory -> budgetCategoryMapper.toDto(budgetCategory, user.getBudget().getMonthlyIncome())).toList();
        //summing all the user fixed costs
        BigDecimal totalFixedCost = sumFixedCosts(budgetCategoryDtoList);
        //flexible spending: user's budget that was not designate.
        BigDecimal flexibleSpending = user.getBudget().getMonthlyIncome().subtract(totalFixedCost);


        return new DashboardDto(
                user.getBudget().getMonthlyIncome(),
                totalFixedCost,
                flexibleSpending,
                budgetCategoryDtoList

        );

    }

    private BigDecimal sumFixedCosts(List<BudgetCategoryDto> budgetCategoryDtoList){

        return budgetCategoryDtoList.stream()
                .flatMap(budgetCategory ->
                        budgetCategory.fixedCosts().stream())
                .map(FixedCostDto::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
