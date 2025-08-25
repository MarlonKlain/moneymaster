package com.moneymaster.moneymaster.controller;


import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budget.BudgetDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import com.moneymaster.moneymaster.service.budget.BudgetService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;

    public BudgetController(BudgetService budgetService, BudgetMapper budgetMapper) {
        this.budgetService = budgetService;
        this.budgetMapper = budgetMapper;
    }

    @PostMapping
    public BudgetDto createBudget(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody BudgetDto budgetDto
    ){
        System.out.println(budgetDto.monthlyIncome());
        Budget createBudget = budgetService.createBudget(currentUser.getId(), budgetMapper.fromDto(budgetDto));
        return budgetMapper.toDto(createBudget);

    }

    @GetMapping
    public Optional<BudgetDto> getBudget(
            @AuthenticationPrincipal UserPrincipal currentuser
    ){
        return budgetService.getBudget(currentuser.getId()).map(budgetMapper::toDto);
    }

    @DeleteMapping(path = "/{budgetId}")
    public void deleteBudget(
            @PathVariable("userId") UUID userID,
            @PathVariable("budgetId") UUID budgetId
    ){
        budgetService.deleteBudget(userID, budgetId);
    }

    @PatchMapping(path = "/{budgetId}")
    public BudgetDto updateBudget(
            @PathVariable("budgetId") UUID budgetId,
            @RequestBody BudgetDto budgetDto
            ){
        Budget budgetUpdated = budgetService.updateBudget(budgetId, budgetMapper.fromDto(budgetDto));

        return budgetMapper.toDto(budgetUpdated);
    }
}
