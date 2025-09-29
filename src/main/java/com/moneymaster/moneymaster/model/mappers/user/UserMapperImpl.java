package com.moneymaster.moneymaster.model.mappers.user;
import com.moneymaster.moneymaster.model.dto.user.UserInformationDto;
import com.moneymaster.moneymaster.model.dto.user.UserLoginDto;
import com.moneymaster.moneymaster.model.dto.user.UserOnboardingStatusDto;
import com.moneymaster.moneymaster.model.dto.user.UserRegistrationDto;
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
    public UserInformationDto createUserInformationDto(User user, String token) {
        return new UserInformationDto(
                token,
                this.createUserOnboardingStatusDto(user),
                null
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


}
