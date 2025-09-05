package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.mappers.budgetcategory.BudgetCategoryMapper;
import com.moneymaster.moneymaster.service.budgetcategory.BudgetCategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user/budget/budget-category")
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
            @AuthenticationPrincipal UserPrincipal currentUser
            ){
        List<BudgetCategory> budgetCategories = budgetCategoryService.getBudgetCategories(currentUser.getBudgetId());

        return budgetCategories.stream().map(budgetCategoryMapper::toDto).toList();

    }

    @PostMapping(path = "/delete")
    public void deleteBudgetList(
        @RequestBody() BudgetCategoryDto budgetCategoryDto
    ){
        budgetCategoryService.deleteBudgetCategory(budgetCategoryMapper.fromDto(budgetCategoryDto));
    }

    @PatchMapping(path = "/{budgetCategoryId}")
    public BudgetCategoryDto updateBudgetCategory(
            @PathVariable("budgetCategoryId") UUID budgetCategoryId,
            @RequestBody BudgetCategoryDto budgetCategoryDto
    ){
        BudgetCategory budgetCategoryUpdated = budgetCategoryService.updateBudgetCategory(budgetCategoryId, budgetCategoryMapper.fromDto(budgetCategoryDto));

        return budgetCategoryMapper.toDto(budgetCategoryUpdated);
    }

    @PostMapping(path = "/update")
    public void updateBudgetCategories(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody List<BudgetCategoryDto> budgetCategoryDto
    ) {

        List<BudgetCategory> budgetCategoryList = budgetCategoryDto.stream().map(budgetCategoryMapper::fromDto).toList();

        budgetCategoryService.updateBudgetCategoriesList(currentUser.getId(), budgetCategoryList);
    }
}
