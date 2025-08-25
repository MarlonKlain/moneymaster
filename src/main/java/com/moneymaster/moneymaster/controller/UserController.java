package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.user.UserDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/register")
    public UserResponseDto createUser(@RequestBody UserDto userDto){
        User createUser = userService.createUser(
                userMapper.fromDto(userDto)
        );

        return userMapper.toDto(createUser, null);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId){
        userService.deleteUser(userId);
    }

    @PostMapping(path = "/login")
    public UserResponseDto userLogin(
            @RequestBody UserDto userCredentials
            ){

        return userService.userLogin(userMapper.fromDto(userCredentials));
    }

    @PatchMapping(path = "/{userId}")
    public UserResponseDto updateUsername(
            @PathVariable("userId") UUID userId,
            @RequestBody UserDto userDto
    ){
        User userUpdated = userService.updateUsername(userId, userMapper.fromDto(userDto));

        return userMapper.toDto(userUpdated, null);
    }

    @GetMapping(path = "/users")
    public List<UserResponseDto> getUsers(){
        List<User> userList = userService.getUsers();
        List<UserResponseDto> userResponseDtos = userList.stream().map(user -> userMapper.toDto(user, null)).toList();
        return userResponseDtos;
    }

    @PatchMapping(path = "/onboarding")
    public void completeOnboarding(
            @AuthenticationPrincipal UserPrincipal currentUser

    ){
        userService.completeOnboarding(currentUser.getId());
    }

}
