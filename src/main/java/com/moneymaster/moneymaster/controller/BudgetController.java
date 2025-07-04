package com.moneymaster.moneymaster.controller;


import com.moneymaster.moneymaster.model.dto.budget.BudgetDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import com.moneymaster.moneymaster.service.budget.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user/{userId}/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;

    public BudgetController(BudgetService budgetService, BudgetMapper budgetMapper) {
        this.budgetService = budgetService;
        this.budgetMapper = budgetMapper;
    }

    @PostMapping
    public BudgetDto createBudget(
            @PathVariable("userId") UUID userId,
            @RequestBody BudgetDto budgetDto
    ){
        Budget createBudget = budgetService.createBudget(userId, budgetMapper.fromDto(budgetDto));
        return budgetMapper.toDto(createBudget);

    }

    @GetMapping
    public Optional<BudgetDto> getBudget(
            @PathVariable("userId") UUID userId
    ){
        return budgetService.getBudget(userId).map(budgetMapper::toDto);
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
