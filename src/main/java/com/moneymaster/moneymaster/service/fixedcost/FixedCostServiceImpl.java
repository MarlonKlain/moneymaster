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
    public List<FixedCost> createFixedCost(UUID currentUserId, List<FixedCost> fixedCostList) {

        List<FixedCost> fixedCosts = new ArrayList<>();

        fixedCostList.forEach(fixedCost -> {

            BudgetCategory budgetCategory = budgetCategoryRepository.getReferenceById(fixedCost.getBudgetCategory().getBudgetCategoryId());

            fixedCost.setBudgetCategory(budgetCategory);

            fixedCosts.add(fixedCost);
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
    public void deleteFixedCost(UUID fixedCostID) {
        if(fixedCostID == null){
            throw new IllegalArgumentException("A Budget Category ID or A Fixed Cost ID must be provided.");
        }

        fixedCostRepository.findById(fixedCostID).orElseThrow(() -> new IllegalArgumentException("Fixed cost not found."));
        fixedCostRepository.deleteById(fixedCostID);

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
