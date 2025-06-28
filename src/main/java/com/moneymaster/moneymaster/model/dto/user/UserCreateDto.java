package com.moneymaster.moneymaster.model.dto.user;

import com.moneymaster.moneymaster.model.entity.Budget;

import java.util.UUID;

public record UserCreateDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String password,
        String username
) {

}
