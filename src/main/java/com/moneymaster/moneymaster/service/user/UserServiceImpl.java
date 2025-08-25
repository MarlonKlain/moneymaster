package com.moneymaster.moneymaster.service.user;


import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.repository.UserRepository;
import com.moneymaster.moneymaster.service.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JWTService jwtService, UserMapper userMapper){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
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

        user.setPassword(encoder.encode(user.getPassword()));
        user.setHasCompletedOnboarding(false);
        return userRepository.save(user);

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
    public UserResponseDto userLogin(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        User userFound = userRepository.findUserByUsername(user.getUsername());
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(user.getUsername());
            return userMapper.toDto(userFound, token);
        }
        return null;
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

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void completeOnboarding(UUID userId) {
        User userToUpdate = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userToUpdate.setHasCompletedOnboarding(true);
        userRepository.save(userToUpdate);
    }

}
