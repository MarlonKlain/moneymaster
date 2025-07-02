package com.moneymaster.moneymaster.model.mappers.user;
import com.moneymaster.moneymaster.model.dto.user.UserCreateDto;
import com.moneymaster.moneymaster.model.dto.user.UserResponseDto;
import com.moneymaster.moneymaster.model.entity.Budget;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.budget.BudgetMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {

    private  final BudgetMapper budgetMapper;

    public UserMapperImpl(BudgetMapper budgetMapper) {
        this.budgetMapper = budgetMapper;
    }


    @Override
    public User fromDto(UserCreateDto userCreateDto) {
        return new User(
                null,
                userCreateDto.firstName(),
                userCreateDto.lastName(),
                userCreateDto.email(),
                userCreateDto.password(),
                userCreateDto.username(),
                null
        );
    }

    @Override
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                budgetMapper.toDto(user.getBudget())
        );
    }
}
