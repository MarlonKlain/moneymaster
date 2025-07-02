package com.moneymaster.moneymaster.service.user;

import com.moneymaster.moneymaster.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    void deleteUser (UUID userId);
    Optional<User> getUser(UUID userId);
}
