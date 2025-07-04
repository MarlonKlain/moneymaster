package com.moneymaster.moneymaster.model.dto.user;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String password,
        String username
) {

}
