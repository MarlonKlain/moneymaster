package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.dto.user.UserDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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

    @GetMapping(path = "/{userId}")
    public Optional<UserResponseDto> getUser(
            @PathVariable("userId") UUID userId
    ){
        return userService.getUser(userId).map(userMapper::toDto);
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
