package com.moneymaster.moneymaster.controller;


import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budget.BudgetDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import com.moneymaster.moneymaster.service.budget.BudgetService;
import com.moneymaster.moneymaster.service.budgetcategory.BudgetCategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;

    public BudgetController(BudgetService budgetService, BudgetMapper budgetMapper, BudgetCategoryService budgetCategoryService) {
        this.budgetService = budgetService;
        this.budgetMapper = budgetMapper;
    }

    @PostMapping
    public BudgetDto createBudget(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody BudgetDto budgetDto
    ){
        Budget createdBudget = budgetService.createBudget(currentUser, budgetMapper.fromDto(budgetDto));
        return budgetMapper.toDto(createdBudget);

    }

    @GetMapping
    public Optional<BudgetDto> getBudget(
            @AuthenticationPrincipal UserPrincipal currentuser
    ){
        return budgetService.getBudgetByUser(currentuser).map(budgetMapper::toDto);
    }

    @DeleteMapping(path = "/{budgetId}")
    public void deleteBudget(
            @AuthenticationPrincipal UserPrincipal currentUser
    ){
        budgetService.deleteBudget(currentUser);
    }

    @PatchMapping(path = "/update")
    public BudgetDto updateBudget(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody BudgetDto budgetDto
            ){
        Budget budgetUpdated = budgetService.updateBudget(currentUser, budgetDto);

        return budgetMapper.toDto(budgetUpdated);
    }
}
