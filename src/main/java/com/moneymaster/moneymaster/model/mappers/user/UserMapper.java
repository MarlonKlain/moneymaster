package com.moneymaster.moneymaster.model.mappers.user;

import com.moneymaster.moneymaster.model.dto.user.*;
import com.moneymaster.moneymaster.model.entity.User;

public interface UserMapper {
    User fromUserLoginDto(UserLoginDto userLoginDto);
    User fromUserRegistrationDto(UserRegistrationDto userRegistrationDto);
    UserInformationDto createUserInformationDto(User user, String token);
    UserOnboardingStatusDto createUserOnboardingStatusDto(User user);


}
