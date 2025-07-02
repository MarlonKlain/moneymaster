package com.moneymaster.moneymaster.service.fixedcost;

import com.moneymaster.moneymaster.model.entity.BudgetCategory;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import com.moneymaster.moneymaster.repository.BudgetCategoryRepository;
import com.moneymaster.moneymaster.repository.FixedCostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FixedCostServiceImpl implements FixedCostService{

    private final FixedCostRepository fixedCostRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;

    public FixedCostServiceImpl(FixedCostRepository fixedCostRepository, BudgetCategoryRepository budgetCategoryRepository) {
        this.fixedCostRepository = fixedCostRepository;
        this.budgetCategoryRepository = budgetCategoryRepository;
    }


    @Override
    public FixedCost createFixedCost(UUID budgetCategoryId, FixedCost fixedCost) {
        if(budgetCategoryId == null){
            throw new IllegalArgumentException("A Budget Category ID must be provided.");
        }

        BudgetCategory budgetCategory = budgetCategoryRepository.getReferenceById(budgetCategoryId);

        return fixedCostRepository.save(new FixedCost(
                null,
                fixedCost.getAmount(),
                fixedCost.getDescription(),
                budgetCategory
        ));
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
    public void deleteFixedCost(UUID budgetCategoryId, UUID fixedCostID) {
        if(budgetCategoryId == null || fixedCostID == null){
            throw new IllegalArgumentException("A Budget Category ID or A Fixed Cost ID must be provided.");
        }
        fixedCostRepository.deleteByBudgetCategory_BudgetCategoryIdAndFixedCostId(budgetCategoryId, fixedCostID);
    }
}
