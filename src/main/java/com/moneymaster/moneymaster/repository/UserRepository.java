package com.moneymaster.moneymaster.repository;

import com.moneymaster.moneymaster.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailOrUsername(String email, String username);
    Optional<User> findByEmail(String email);
}
