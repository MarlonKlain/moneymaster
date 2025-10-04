package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.budgetcategory.BudgetCategoryDto;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.mappers.budgetcategory.BudgetCategoryMapper;
import com.moneymaster.moneymaster.service.budget.BudgetService;
import com.moneymaster.moneymaster.service.budgetcategory.BudgetCategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/budget-category")
public class BudgetCategoryController {

    private final BudgetCategoryService budgetCategoryService;
    private final BudgetCategoryMapper budgetCategoryMapper;
    private final BudgetService budgetService;


    public BudgetCategoryController(BudgetCategoryService budgetCategoryService, BudgetCategoryMapper budgetCategoryMapper, BudgetService budgetService) {
        this.budgetCategoryService = budgetCategoryService;
        this.budgetCategoryMapper = budgetCategoryMapper;
        this.budgetService = budgetService;
    }

    @PostMapping(path = "/create")
    public BudgetCategoryDto createBudgetCategory(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody BudgetCategoryDto budgetCategoryDto
            ){
        return budgetCategoryService.createBudgetCategory(currentUser, budgetCategoryDto);
    }

    @GetMapping
    public List<BudgetCategoryDto> getBudgetCategories(
            @AuthenticationPrincipal UserPrincipal currentUser
            ){
        List<BudgetCategory> budgetCategories = budgetCategoryService.getBudgetCategories(currentUser);

        return budgetCategories.stream().map(budgetCategory -> budgetCategoryMapper.toDto(budgetCategory, new BigDecimal("0"))).toList();

    }

    @GetMapping(path = "/{budgetCategoryId}")
    public BudgetCategoryDto getBudgetCategory(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable("budgetCategoryId") UUID budgetCategoryId
    ){

        return budgetCategoryService.getBudgetCategory(currentUser, budgetCategoryId);
    }

    @PostMapping(path = "/delete")
    public void deleteBudgetList(
        @RequestBody BudgetCategoryDto budgetCategoryDto
    ){

        budgetCategoryService.deleteBudgetCategory(budgetCategoryMapper.fromDto(budgetCategoryDto));
    }

    @PatchMapping(path = "/{budgetCategoryId}")
    public BudgetCategoryDto updateBudgetCategory(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable("budgetCategoryId") UUID budgetCategoryId,
            @RequestBody BudgetCategoryDto budgetCategoryDto
    ){

        return budgetCategoryService.updateBudgetCategory(currentUser, budgetCategoryId, budgetCategoryDto);
    }

    @PostMapping(path = "/update")
    public void updateBudgetCategories(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody List<BudgetCategoryDto> budgetCategoryDto
    ) {

        List<BudgetCategory> budgetCategoryList = budgetCategoryDto.stream().map(budgetCategoryMapper::fromDto).toList();

        budgetCategoryService.updateBudgetCategoriesList(currentUser, budgetCategoryList);
    }
}
