package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.dto.fixedcost.FixedCostDto;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import com.moneymaster.moneymaster.model.mappers.fixedcost.FixedCostMapper;
import com.moneymaster.moneymaster.service.fixedcost.FixedCostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user/{userId}/budget/{budgetId}/budget-category/{budgetCategoryId}/fixed-cost")
public class FixedCostController {

    private final FixedCostMapper fixedCostMapper;
    private final FixedCostService fixedCostService;

    public FixedCostController(FixedCostMapper fixedCostMapper, FixedCostService fixedCostService) {
        this.fixedCostMapper = fixedCostMapper;
        this.fixedCostService = fixedCostService;
    }


    @PostMapping
    public FixedCostDto createFixedCost(
            @PathVariable("budgetCategoryId") UUID budgetCategoryID,
            @RequestBody FixedCostDto fixedCostDto
            ){
        FixedCost createdFixedCost = fixedCostService.createFixedCost(budgetCategoryID, fixedCostMapper.fromDto(fixedCostDto));

        return fixedCostMapper.toDto(createdFixedCost);
    }

    @GetMapping
    public List<FixedCostDto> getFixedCost(
            @PathVariable("budgetCategoryId") UUID budgetCategoryID
    ){
        List<FixedCost> fixedCosts = fixedCostService.getFixedCosts(budgetCategoryID);

        //Returns a list of FixedCostDTOs
        return fixedCosts.stream().map(fixedCostMapper::toDto).toList();

    }

    @DeleteMapping(path = "/{fixedCostId}")
    public void deleteFixedCost(
            @PathVariable("budgetCategoryId") UUID budgetCategoryId,
            @PathVariable("fixedCostId") UUID fixedCostId
    ){
        fixedCostService.deleteFixedCost(budgetCategoryId, fixedCostId);
    }

    @PatchMapping(path = "/{fixedCostId}")
    public FixedCostDto updateFixedCost(
            @PathVariable("fixedCostId") UUID fixedCostId,
            @RequestBody FixedCostDto fixedCostDto
            ){
        FixedCost fixedCostUpdated = fixedCostService.updateFixedCost(fixedCostId, fixedCostMapper.fromDto(fixedCostDto));

        return fixedCostMapper.toDto(fixedCostUpdated);
    }
}
