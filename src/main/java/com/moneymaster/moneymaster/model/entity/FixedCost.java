package com.moneymaster.moneymaster.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Fixed_Costs")
public class FixedCost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "fixed_cost_id")
    private UUID fixedCostId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_category_id")
    private BudgetCategory budgetCategory;

    public FixedCost() {
    }

    public FixedCost(UUID fixedCostId, BigDecimal amount, String description, BudgetCategory budgetCategory) {
        this.fixedCostId = fixedCostId;
        this.amount = amount;
        this.description = description;
        this.budgetCategory = budgetCategory;
    }

    public UUID getFixedCostId() {
        return fixedCostId;
    }

    public void setFixedCostId(UUID fixedCostId) {
        this.fixedCostId = fixedCostId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FixedCost fixedCost = (FixedCost) o;
        return Objects.equals(fixedCostId, fixedCost.fixedCostId) && Objects.equals(amount, fixedCost.amount) && Objects.equals(description, fixedCost.description) && Objects.equals(budgetCategory, fixedCost.budgetCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fixedCostId, amount, description, budgetCategory);
    }

    @Override
    public String toString() {
        return "FixedCost{" +
                "fixedCostId=" + fixedCostId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
