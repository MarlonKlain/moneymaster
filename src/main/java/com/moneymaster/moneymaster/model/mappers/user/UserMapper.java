package com.moneymaster.moneymaster.model.mappers.user;

import com.moneymaster.moneymaster.model.dto.user.UserDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;

public interface UserMapper {
    User fromDto(UserDto userDto);
    UserResponseDto toDto(User user, String token);
}
