package com.moneymaster.moneymaster.service.user;

import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
    public User userLogin(String email, String password) {
        User userFound = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found."));
        if(!Objects.equals(password, userFound.getPassword())){
            throw new IllegalArgumentException("Password invalid");
        } else {
            return userFound;
        }
    }

    @Override
    public User updateUsername(UUID userId, User user) {
        if (user.getUserId() != null) {
            throw new IllegalArgumentException("A User ID must no be provided by the client!.");
        }

        User userToUpdate = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        userToUpdate.setUsername(user.getUsername());

        return userRepository.save(userToUpdate);
    }

}
