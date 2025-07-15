package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.dto.user.UserDto;
import com.moneymaster.moneymaster.model.dto.user.UserLoginDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserDto userDto){
        User createUser = userService.createUser(
                userMapper.fromDto(userDto)
        );

        return userMapper.toDto(createUser);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId){
        userService.deleteUser(userId);
    }

    @PostMapping(path = "/login")
    public UserResponseDto userLogin(
            @RequestBody UserDto userCredentials
            ){

        User userFound = userService.userLogin(userCredentials.email(), userCredentials.password());

        return userMapper.toDto(userFound);
    }

    @PatchMapping(path = "/{userId}")
    public UserResponseDto updateUsername(
            @PathVariable("userId") UUID userId,
            @RequestBody UserDto userDto
    ){
        User userUpdated = userService.updateUsername(userId, userMapper.fromDto(userDto));

        return userMapper.toDto(userUpdated);
    }

}
