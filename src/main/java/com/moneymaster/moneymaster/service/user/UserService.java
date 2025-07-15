package com.moneymaster.moneymaster.service.user;

import com.moneymaster.moneymaster.model.entity.User;

import java.util.UUID;

public interface UserService {
    User createUser(User user);
    void deleteUser (UUID userId);
    User userLogin(String email, String password);
    User updateUsername(UUID userId, User user);
}
