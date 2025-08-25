package com.moneymaster.moneymaster.model.mappers.user;
import com.moneymaster.moneymaster.model.dto.user.UserDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    private  final BudgetMapper budgetMapper;

    public UserMapperImpl(BudgetMapper budgetMapper) {
        this.budgetMapper = budgetMapper;
    }


    @Override
    public User fromDto(UserDto userDto) {
        return new User(
                null,
                userDto.firstName(),
                userDto.lastName(),
                userDto.email(),
                userDto.password(),
                userDto.username(),
                userDto.hasCompletedOnboarding(),
                null
        );
    }

    @Override
    public UserResponseDto toDto(User user, String token) {

        if(user.getBudget() != null) {
        return new UserResponseDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.hasCompletedOnboarding(),
                token,
                budgetMapper.toDto(user.getBudget())
        );

        } else {
            return new UserResponseDto(
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword(),
                    user.hasCompletedOnboarding(),
                    token,
                    null
            );
        }
    }
}
