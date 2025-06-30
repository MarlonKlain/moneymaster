package com.moneymaster.moneymaster.controller;


import com.moneymaster.moneymaster.model.dto.BudgetDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import com.moneymaster.moneymaster.service.budget.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user/{user_id}/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;

    public BudgetController(BudgetService budgetService, BudgetMapper budgetMapper) {
        this.budgetService = budgetService;
        this.budgetMapper = budgetMapper;
    }

    @PostMapping
    public BudgetDto createBudget(
            @PathVariable("user_id") UUID userId,
            @RequestBody BudgetDto budgetDto
    ){
        Budget createBudget = budgetService.createBudget(userId, budgetMapper.fromDto(budgetDto));
        return budgetMapper.toDto(createBudget);

    }

    @GetMapping
    public Optional<BudgetDto> getBudget(
            @PathVariable("user_id") UUID userId
    ){
        return budgetService.getBudget(userId).map(budgetMapper::toDto);
    }

    @DeleteMapping(path = "/{budget_id}")
    public void deleteBudget(
            @PathVariable("user_id") UUID userID,
            @PathVariable("budget_id") UUID budgetId
    ){
        budgetService.deleteBudget(userID, budgetId);
    }
}
