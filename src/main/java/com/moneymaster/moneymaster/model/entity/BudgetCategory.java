package com.moneymaster.moneymaster.model.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Budget_Categories")
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "budget_category_id")
    private UUID budgetCategoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @Column(name = "percentage", nullable = false)
    private Double percentage;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name= "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "budgetCategory", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FixedCost> fixedCosts;

    public BudgetCategory(){

    }

    public BudgetCategory(UUID budgetCategoryId, Budget budget, Double percentage, String name, String imageUrl, List<FixedCost> fixedCosts) {
        this.budgetCategoryId = budgetCategoryId;
        this.budget = budget;
        this.percentage = percentage;
        this.name = name;
        this.imageUrl = imageUrl;
        this.fixedCosts = fixedCosts;
    }

    public UUID getBudgetCategoryId() {
        return budgetCategoryId;
    }

    public void setBudgetCategoryId(UUID budgetCategoryId) {
        this.budgetCategoryId = budgetCategoryId;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<FixedCost> getFixedCosts() {
        return fixedCosts;
    }

    public void setFixedCosts(List<FixedCost> fixedCosts) {
        this.fixedCosts = fixedCosts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BudgetCategory that = (BudgetCategory) o;
        return Objects.equals(budgetCategoryId, that.budgetCategoryId) && Objects.equals(budget, that.budget) && Objects.equals(percentage, that.percentage) && Objects.equals(name, that.name) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(fixedCosts, that.fixedCosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetCategoryId, budget, percentage, name, imageUrl, fixedCosts);
    }

    @Override
    public String toString() {
        return "BudgetCategory{" +
                "budgetCategoryId=" + budgetCategoryId +
                ", budget=" + budget +
                ", percentage=" + percentage +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", fixedCosts=" + fixedCosts +
                '}';
    }
}
