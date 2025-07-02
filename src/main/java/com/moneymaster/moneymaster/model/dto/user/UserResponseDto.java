package com.moneymaster.moneymaster.model.dto.user;

import com.moneymaster.moneymaster.model.dto.BudgetDto;
import com.moneymaster.moneymaster.model.entity.Budget;

import java.util.List;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String firstName,
        String lastNamel,
        BudgetDto budgetDto
) {
}
