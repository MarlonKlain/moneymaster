package com.moneymaster.moneymaster.service.user;


import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.user.UserLoginResponseDto;
import com.moneymaster.moneymaster.model.dto.user.UserLoginDto;
import com.moneymaster.moneymaster.model.dto.user.UserRegistrationDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.repository.UserRepository;
import com.moneymaster.moneymaster.service.jwt.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Transactional
    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {

        User userToRegister = userMapper.fromUserRegistrationDto(userRegistrationDto);
        //checks if the frontend is not providing a id. For registration, it always should be null
        if(userToRegister.getUserId() != null){
            throw new IllegalArgumentException("A User ID must no be provided by the client!.");
        }

        //Look if the credentials provided by the user already exists
        Optional<User> userExists = userRepository.findByEmailOrUsername(userToRegister.getEmail(), userToRegister.getUsername());
        if(userExists.isPresent()){
            throw new IllegalArgumentException("The credentials provided were already taken!");
        }

        //encrypt the password
        userToRegister.setPassword(encoder.encode(userToRegister.getPassword()));

        //register the user in the database
        return userRepository.save(userToRegister);

    }

    @Override
    public void deleteUser(UserPrincipal currentUser) {
        if(currentUser.getId() == null){
            throw new IllegalArgumentException("ID was not provided!.");
        } else {
            userRepository.deleteById(currentUser.getId());
        }
    }

    @Override
    public UserLoginResponseDto loginUser(UserLoginDto userLoginDto) {
        User userToLogin = userMapper.fromUserLoginDto(userLoginDto);
        //todo Enables the login also by email
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userToLogin.getUsername(), userToLogin.getPassword()));
        User userFound = userRepository.findUserByUsername(userToLogin.getUsername());
        if(!authentication.isAuthenticated()){
            throw new IllegalArgumentException("Authentication went wrong, please try again.");
        }
            String token = jwtService.generateToken(userToLogin.getUsername());
            return userMapper.createUserInformationDto(userFound, token);
    }

    @Override
    public User updateUserOnboardingStatus(UserPrincipal currentUser) {
        User userToUpdate = userRepository.findById(currentUser.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userToUpdate.setHasCompletedOnboarding(true);
        return userRepository.save(userToUpdate);
    }

    @Override
    public User getUser(UserPrincipal currentUser) {
        if(currentUser.getId() == null){
            throw new IllegalArgumentException("A User ID must be provided by the user!");
        }

        return userRepository.findById(currentUser.getId()).orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

}
