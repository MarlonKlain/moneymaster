package com.moneymaster.moneymaster.model.dto;

public record ErrorResponseDto(
        int Status,
        String message,
        String description
) {
}
