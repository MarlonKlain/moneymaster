package com.moneymaster.moneymaster.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "budget_id")
    private UUID budgetId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "montlhy_income", nullable = false)
    private BigDecimal monthlyIncome;

    public Budget(){

    }

    public Budget(UUID budgetId, User user, BigDecimal monthlyIncome){
        this.budgetId = budgetId;
        this.user = user;
        this.monthlyIncome = monthlyIncome;
    }

    public UUID getBudgetId(){
        return budgetId;
    }

    public User getUser(){
        return user;
    }

    public BigDecimal getMonthlyIncome(){
        return monthlyIncome;
    }

    public void setBudgetId(UUID budgetId){
        this.budgetId = budgetId;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome){
        this.monthlyIncome = monthlyIncome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return Objects.equals(budgetId, budget.budgetId) && Objects.equals(user, budget.user) && Objects.equals(monthlyIncome, budget.monthlyIncome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, user, monthlyIncome);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", user=" + user +
                ", monthlyIncome=" + monthlyIncome +
                '}';
    }
}
