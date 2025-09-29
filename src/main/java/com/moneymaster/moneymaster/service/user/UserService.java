package com.moneymaster.moneymaster.service.user;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.user.UserInformationDto;
import com.moneymaster.moneymaster.model.dto.user.UserLoginDto;
import com.moneymaster.moneymaster.model.dto.user.UserRegistrationDto;
import com.moneymaster.moneymaster.model.entity.User;

import java.util.UUID;

public interface UserService {
    User registerUser(UserRegistrationDto userRegistrationDto);
    void deleteUser (UserPrincipal currentUser);
    UserInformationDto loginUser(UserLoginDto userLoginDto);
    User updateUserOnboardingStatus(UserPrincipal currentUser);
//    User updateUsername(UUID userId, User user);
//    List<User> getUsers();
    User getUser(UUID userId);

}
