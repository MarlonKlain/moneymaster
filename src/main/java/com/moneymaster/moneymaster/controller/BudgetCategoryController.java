package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.dto.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.mappers.budgetcategory.BudgetCategoryMapper;
import com.moneymaster.moneymaster.service.budgetcategory.BudgetCategoryService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user/{userId}/budget/{budgetId}/budget-category")
public class BudgetCategoryController {

    private final BudgetCategoryService budgetCategoryService;
    private final BudgetCategoryMapper budgetCategoryMapper;


    public BudgetCategoryController(BudgetCategoryService budgetCategoryService, BudgetCategoryMapper budgetCategoryMapper) {
        this.budgetCategoryService = budgetCategoryService;
        this.budgetCategoryMapper = budgetCategoryMapper;
    }

    @PostMapping
    public BudgetCategoryDto createBudgetCategory(
            @PathVariable("budgetId") UUID budgetId,
            @RequestBody BudgetCategoryDto budgetCategoryDto
            ){
        BudgetCategory createdBudget = budgetCategoryService.createBudgetCategory(budgetId, budgetCategoryMapper.fromDto(budgetCategoryDto));

        return budgetCategoryMapper.toDto(createdBudget);

    }

    @GetMapping
    public List<BudgetCategoryDto> getBudgetCategory(
            @PathVariable("budgetId") UUID budgetId
    ){
        List<BudgetCategory> budgetCategories = budgetCategoryService.getBudgetCategories(budgetId);

        return budgetCategories.stream().map(budgetCategoryMapper::toDto).toList();

    }

    @DeleteMapping(path = "/{budgetCategoryId}")
    public void deleteBudgetList(
        @PathVariable("budgetId") UUID budgetId,
        @PathVariable("budgetCategoryId") UUID budgetCategoryId
    ){
        budgetCategoryService.deleteBudgetCategory(budgetId, budgetCategoryId);
    }

}
