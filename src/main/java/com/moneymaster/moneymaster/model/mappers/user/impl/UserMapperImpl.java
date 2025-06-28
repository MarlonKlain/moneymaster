package com.moneymaster.moneymaster.model.mappers.user.impl;
import com.moneymaster.moneymaster.model.dto.user.UserCreateDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;

import java.util.Optional;


public class UserMapperImpl implements UserMapper {

    @Override
    public User fromDto(UserCreateDto userCreateDto) {
        return new User(
                userCreateDto.id(),
                userCreateDto.firstName(),
                userCreateDto.lastName(),
                userCreateDto.email(),
                userCreateDto.password(),
                userCreateDto.username(),
                null
        );
    }

    @Override
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
