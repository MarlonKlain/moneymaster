package com.moneymaster.moneymaster.service.user;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.user.UserLoginResponseDto;
import com.moneymaster.moneymaster.model.dto.user.UserLoginDto;
import com.moneymaster.moneymaster.model.dto.user.UserRegistrationDto;
import com.moneymaster.moneymaster.model.entity.User;

public interface UserService {
    User registerUser(UserRegistrationDto userRegistrationDto);
    void deleteUser (UserPrincipal currentUser);
    UserLoginResponseDto loginUser(UserLoginDto userLoginDto);
    User updateUserOnboardingStatus(UserPrincipal currentUser);
//    User updateUsername(UUID userId, User user);
//    List<User> getUsers();
    User getUser(UserPrincipal currentUser);

}
