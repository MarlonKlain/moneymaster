package com.moneymaster.moneymaster.model.mappers.fixedcost;

import com.moneymaster.moneymaster.model.dto.FixedCostDto;
import com.moneymaster.moneymaster.model.entity.FixedCost;
import org.springframework.stereotype.Component;

@Component
public class FixedCostMapperImpl implements FixedCostMapper {

    @Override
    public FixedCost fromDto(FixedCostDto fixedCostDto) {
        return new FixedCost(
                fixedCostDto.fixedCostId(),
                fixedCostDto.amount(),
                fixedCostDto.description(),
                null
        );
    }

    @Override
    public FixedCostDto toDto(FixedCost fixedCost) {
        return new FixedCostDto(
                fixedCost.getFixedCostId(),
                fixedCost.getAmount(),
                fixedCost.getDescription()
        );
    }
}
