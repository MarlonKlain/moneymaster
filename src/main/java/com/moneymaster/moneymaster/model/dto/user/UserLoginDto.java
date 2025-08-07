package com.moneymaster.moneymaster.model.dto.user;

import java.util.UUID;

public record UserLoginDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String username,
        String JWTtoken
        ) {
}
