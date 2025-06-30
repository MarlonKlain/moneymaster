package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.dto.user.UserCreateDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public UserResponseDto createUser(@RequestBody UserCreateDto userCreateDto){
        User createUser = userService.createUser(
                userMapper.fromDto(userCreateDto)
        );

        return userMapper.toDto(createUser);
    }

    @DeleteMapping(path = "/{user_id}")
    public void deleteUser(@PathVariable("user_id") UUID userId){
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return userList.stream().map(userMapper::toDto).toList();
    }
}
