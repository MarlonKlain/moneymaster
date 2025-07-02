package com.moneymaster.moneymaster.service.user;

import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //todo
    //@transactional
    @Override
    public User createUser(User user) {
        if(user.getUserId() != null){
            throw new IllegalArgumentException("A User ID must no be provided by the client!.");
        }

        Optional<User> userExists = userRepository.findByEmailOrUsername(user.getEmail(), user.getUsername());

        if(userExists.isPresent()){
            throw new IllegalArgumentException("The credentials provided were already taken!");
        }

        return userRepository.save(
                new User(
                        null,
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getUsername(),
                        null
                )
        );
    }

    @Override
    public void deleteUser(UUID userId) {
        if(userId == null){
            throw new IllegalArgumentException("ID was not provided!.");
        } else {
            userRepository.deleteById(userId);
        }
    }

    @Override
    public Optional<User> getUser(UUID userId) {
        return userRepository.findById(userId);
    }
}
