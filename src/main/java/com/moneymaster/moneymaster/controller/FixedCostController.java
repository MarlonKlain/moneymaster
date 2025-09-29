package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.fixedcost.FixedCostDto;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import com.moneymaster.moneymaster.model.mappers.fixedcost.FixedCostMapper;
import com.moneymaster.moneymaster.service.fixedcost.FixedCostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api")
public class FixedCostController {

    private final FixedCostMapper fixedCostMapper;
    private final FixedCostService fixedCostService;

    public FixedCostController(FixedCostMapper fixedCostMapper, FixedCostService fixedCostService) {
        this.fixedCostMapper = fixedCostMapper;
        this.fixedCostService = fixedCostService;
    }


    @PostMapping(path = "/fixed-cost")
    public List<FixedCostDto> createFixedCost(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody List<FixedCostDto> fixedCostDtoList
            ){

        List<FixedCost> createdFixedCost = fixedCostService.createFixedCost(currentUser.getId(), fixedCostDtoList);

        return createdFixedCost.stream().map(fixedCostMapper::toDto).toList();
    }

    @GetMapping
    public List<FixedCostDto> getFixedCost(
            @PathVariable("budgetCategoryId") UUID budgetCategoryID
    ){
        List<FixedCost> fixedCosts = fixedCostService.getFixedCosts(budgetCategoryID);

        //Returns a list of FixedCostDTOs
        return fixedCosts.stream().map(fixedCostMapper::toDto).toList();

    }

    @PostMapping(path = "/fixed-cost/delete")
    public void deleteFixedCost(
            @RequestBody FixedCostDto fixedCostDto
    ){
        fixedCostService.deleteFixedCost(fixedCostDto);
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
