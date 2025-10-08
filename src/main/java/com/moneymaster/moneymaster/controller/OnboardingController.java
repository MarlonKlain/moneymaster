package com.moneymaster.moneymaster.controller;


import com.moneymaster.moneymaster.model.UserPrincipal;
import com.moneymaster.moneymaster.model.dto.ServerResponseDto;
import com.moneymaster.moneymaster.model.dto.user.UserOnboardingStatusDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.user.UserMapper;
import com.moneymaster.moneymaster.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/onboarding")
public class OnboardingController {

    private final UserService userService;
    private final UserMapper userMapper;

    public OnboardingController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping(path = "/status")
    public UserOnboardingStatusDto getUserOnboardingStatus(
            @AuthenticationPrincipal UserPrincipal currentUser
    ){

        User user = userService.getUser(currentUser);
        return userMapper.createUserOnboardingStatusDto(user);
    }

    @PatchMapping(path="/complete")
    public ServerResponseDto completeUserOnboarding(
            @AuthenticationPrincipal UserPrincipal currentUser
    )
    {
        User userUpdated = userService.updateUserOnboardingStatus(currentUser);
        return new ServerResponseDto(
                "OK"
        );
    }
}
