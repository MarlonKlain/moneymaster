package com.moneymaster.moneymaster.model.mappers.user;
import com.moneymaster.moneymaster.model.dto.user.*;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    private final BudgetMapper budgetMapper;

    public UserMapperImpl(BudgetMapper budgetMapper) {
        this.budgetMapper = budgetMapper;
    }


    @Override
    public User fromUserLoginDto(UserLoginDto userLoginDto) {
        return new User(
                null,
                null,
                null,
                null,
                userLoginDto.password(),
                userLoginDto.username(),
                false,
                false,
                false,
                false,
                null
        );
    }

    @Override
    public User fromUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
        return new User(
                null,
                userRegistrationDto.firstName(),
                userRegistrationDto.lastName(),
                userRegistrationDto.email(),
                userRegistrationDto.password(),
                userRegistrationDto.username(),
                false,
                false,
                false,
                false,
                null
        );
    }

    @Override
    public UserLoginResponseDto createUserInformationDto(User user, String token) {
        return new UserLoginResponseDto(
                token,
                this.createUserOnboardingStatusDto(user)
        );
    }

    @Override
    public UserOnboardingStatusDto createUserOnboardingStatusDto(User user) {
        return new UserOnboardingStatusDto(
                user.hasCompletedOnboarding(),
                user.isHasSetMonthlyIncome(),
                user.isHasSetBudgetCategories(),
                user.isHasSetFixedCosts()
        );
    }

    @Override
    public UserProfileInformationDto createUserProfileInformationDto(User user) {
        return new UserProfileInformationDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername()
        );
    }


}
