package com.moneymaster.moneymaster.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "onboarding", nullable = false)
    private boolean hasCompletedOnboarding = false;

    @Column(name = "hasSetMonthlyIncome", nullable = false)
    private boolean hasSetMonthlyIncome = false;

    @Column(name = "hasSetBudgetCategories", nullable = false)
    private boolean hasSetBudgetCategories = false;

    @Column(name = "hasSetFixedCosts", nullable = false)
    private boolean hasSetFixedCosts = false;

    @OneToOne(mappedBy = "user", cascade ={CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Budget budget;

    public User() {

    }

    public User(UUID userId, String firstName, String lastName, String email, String password, String username, boolean hasCompletedOnboarding, boolean hasSetMonthlyIncome, boolean hasSetBudgetCategories, boolean hasSetFixedCosts, Budget budget) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.hasCompletedOnboarding = hasCompletedOnboarding;
        this.hasSetMonthlyIncome = hasSetMonthlyIncome;
        this.hasSetBudgetCategories = hasSetBudgetCategories;
        this.hasSetFixedCosts = hasSetFixedCosts;
        this.budget = budget;
    }

    public boolean isHasSetMonthlyIncome() {
        return hasSetMonthlyIncome;
    }

    public void setHasSetMonthlyIncome(boolean hasSetMonthlyIncome) {
        this.hasSetMonthlyIncome = hasSetMonthlyIncome;
    }

    public boolean isHasSetBudgetCategories() {
        return hasSetBudgetCategories;
    }

    public void setHasSetBudgetCategories(boolean hasSetBudgetCategories) {
        this.hasSetBudgetCategories = hasSetBudgetCategories;
    }

    public boolean isHasSetFixedCosts() {
        return hasSetFixedCosts;
    }

    public void setHasSetFixedCosts(boolean hasSetFixedCosts) {
        this.hasSetFixedCosts = hasSetFixedCosts;
    }

    public boolean hasCompletedOnboarding() {
        return hasCompletedOnboarding;
    }

    public void setHasCompletedOnboarding(boolean hasCompletedOnboarding) {
        this.hasCompletedOnboarding = hasCompletedOnboarding;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return hasCompletedOnboarding == user.hasCompletedOnboarding && hasSetMonthlyIncome == user.hasSetMonthlyIncome && hasSetBudgetCategories == user.hasSetBudgetCategories && hasSetFixedCosts == user.hasSetFixedCosts && Objects.equals(userId, user.userId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(username, user.username) && Objects.equals(budget, user.budget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, password, username, hasCompletedOnboarding, hasSetMonthlyIncome, hasSetBudgetCategories, hasSetFixedCosts, budget);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", hasCompletedOnboarding=" + hasCompletedOnboarding +
                ", hasSetMonthlyIncome=" + hasSetMonthlyIncome +
                ", hasSetBudgetCategories=" + hasSetBudgetCategories +
                ", hasSetFixedCosts=" + hasSetFixedCosts +
                ", budget=" + budget +
                '}';
    }
}