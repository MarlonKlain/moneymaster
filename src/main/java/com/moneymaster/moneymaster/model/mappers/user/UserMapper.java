package com.moneymaster.moneymaster.model.mappers.user;

import com.moneymaster.moneymaster.model.dto.user.UserCreateDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;

public interface UserMapper {
    User fromDto(UserCreateDto userCreateDto);
    UserResponseDto toDto(User user);
}
