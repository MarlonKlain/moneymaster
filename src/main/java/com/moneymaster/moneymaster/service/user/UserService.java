package com.moneymaster.moneymaster.service.user;

import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    void deleteUser (UUID userId);
    UserResponseDto userLogin(User user);
    User updateUsername(UUID userId, User user);
    List<User> getUsers();
    void completeOnboarding(UUID userId);
    User getUser(UUID userId);

}
