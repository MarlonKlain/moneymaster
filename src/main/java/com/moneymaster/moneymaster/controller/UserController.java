package com.moneymaster.moneymaster.controller;

import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.ServerResponseDto;
import com.moneymaster.moneymaster.model.dto.user.UserInformationDto;
import com.moneymaster.moneymaster.model.dto.user.UserLoginDto;
import com.moneymaster.moneymaster.model.dto.user.UserOnboardingStatusDto;
import com.moneymaster.moneymaster.model.dto.user.UserRegistrationDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/user")
public class UserController{

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/register")
    public ServerResponseDto registerUser(@RequestBody UserRegistrationDto userRegistrationDto){

        User userCreated = userService.registerUser(userRegistrationDto);

        return new ServerResponseDto(
                "OK"
        );
    }

    @PostMapping(path = "/login")
    public UserInformationDto loginUser(
            @RequestBody UserLoginDto userLoginDto
            ){
        return userService.loginUser(userLoginDto);
    }



//
//    @DeleteMapping(path = "/{userId}")
//    public void deleteUser(@PathVariable("userId") UUID userId){
//        userService.deleteUser(userId);
//    }
//

//
//    @PatchMapping(path = "/{userId}")
//    public UserResponseDto updateUsername(
//            @PathVariable("userId") UUID userId,
//            @RequestBody UserDto userDto
//    ){
//        User userUpdated = userService.updateUsername(userId, userMapper.fromDto(userDto));
//
//        return userMapper.createUserInformationDto(userUpdated, , null);
//    }
//
//    @GetMapping(path = "/users")
//    public List<UserResponseDto> getUsers(){
//        List<User> userList = userService.getUsers();
//        return userList.stream().map(user -> userMapper.createUserInformationDto(user, , null)).toList();
//    }
//

//
//


}
