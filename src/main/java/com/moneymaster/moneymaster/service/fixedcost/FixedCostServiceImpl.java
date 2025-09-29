package com.moneymaster.moneymaster.service.fixedcost;

import com.moneymaster.moneymaster.model.dto.fixedcost.FixedCostDto;
import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import com.moneymaster.moneymaster.model.entity.User;
import com.moneymaster.moneymaster.model.mappers.fixedcost.FixedCostMapper;
import com.moneymaster.moneymaster.repository.BudgetCategoryRepository;
import com.moneymaster.moneymaster.repository.FixedCostRepository;
import com.moneymaster.moneymaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FixedCostServiceImpl implements FixedCostService{

    private final FixedCostRepository fixedCostRepository;
    private final UserRepository userRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final FixedCostMapper fixedCostMapper;

    public FixedCostServiceImpl(FixedCostRepository fixedCostRepository, UserRepository userRepository, BudgetCategoryRepository budgetCategoryRepository, FixedCostMapper fixedCostMapper) {
        this.fixedCostRepository = fixedCostRepository;
        this.userRepository = userRepository;
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.fixedCostMapper = fixedCostMapper;
    }

    @Override
    @Transactional
    public List<FixedCost> createFixedCost(UUID currentUserId, List<FixedCostDto> fixedCostDtoListDto) {

        List<FixedCost> fixedCosts = new ArrayList<>();
        fixedCostDtoListDto.forEach(fixedCostDto -> {
            BudgetCategory budgetCategory = budgetCategoryRepository.getReferenceById(fixedCostDto.budgetCategoryId());
            FixedCost fixedCostToUpdate = new FixedCost(
                    null,
                    fixedCostDto.amount(),
                    fixedCostDto.description(),
                    budgetCategory
            );
            fixedCosts.add(fixedCostToUpdate);
        });

        User user = userRepository.findById(currentUserId).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        user.setHasSetFixedCosts(true);
        userRepository.save(user);

        return fixedCostRepository.saveAll(fixedCosts);
    }

    @Override
    public List<FixedCost> getFixedCosts(UUID budgetCategoryId) {
        if(budgetCategoryId == null){
            throw new IllegalArgumentException("A Budget Category ID must be provided.");
        }
        return fixedCostRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteFixedCost(FixedCostDto fixedCostDto) {
        if(fixedCostDto == null){
            throw new IllegalArgumentException("A Budget Category ID or A Fixed Cost ID must be provided.");
        }

        fixedCostRepository.findById(fixedCostDto.fixedCostId()).orElseThrow(() -> new IllegalArgumentException("Fixed cost not found."));
        fixedCostRepository.deleteById(fixedCostDto.fixedCostId());

    }

    @Override
    public FixedCost updateFixedCost(UUID fixedCostId, FixedCost fixedCost) {
        if(fixedCostId == null){
            throw new IllegalArgumentException("A Fixed Cost ID must be provided.");
        }

        FixedCost fixedCostToUpdate = fixedCostRepository.findById(fixedCostId).orElseThrow(() -> new IllegalArgumentException("Fixed Cost not found"));

        if(fixedCost.getAmount() != null){
            fixedCostToUpdate.setAmount(fixedCost.getAmount());
        }

        if(fixedCost.getDescription() != null){
            fixedCostToUpdate.setDescription(fixedCost.getDescription());
        }

        return fixedCostRepository.save(fixedCostToUpdate);
    }
}
