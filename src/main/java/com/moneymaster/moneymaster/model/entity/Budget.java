package com.moneymaster.moneymaster.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "budget_id")
    private UUID budgetId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "monthly_income", nullable = false)
    private BigDecimal monthlyIncome;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BudgetCategory> budgetCategories;

    public Budget(){

    }

    public Budget(UUID budgetId, User user, BigDecimal monthlyIncome, List<BudgetCategory> budgetCategories) {
        this.budgetId = budgetId;
        this.user = user;
        this.monthlyIncome = monthlyIncome;
        this.budgetCategories = budgetCategories;
    }

    public UUID getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(UUID budgetId) {
        this.budgetId = budgetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public List<BudgetCategory> getBudgetCategories() {
        return budgetCategories;
    }

    public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
        this.budgetCategories = budgetCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return Objects.equals(budgetId, budget.budgetId) && Objects.equals(user, budget.user) && Objects.equals(monthlyIncome, budget.monthlyIncome) && Objects.equals(budgetCategories, budget.budgetCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, user, monthlyIncome, budgetCategories);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", user=" + user +
                ", monthlyIncome=" + monthlyIncome +
                ", budgetCategories=" + budgetCategories +
                '}';
    }
}
