ALTER TABLE Users
ADD COLUMN hasSetMonthlyIncome BOOLEAN DEFAULT false,
ADD COLUMN hasSetBudgetCategories BOOLEAN DEFAULT false,
ADD COLUMN hasSetFixedCosts BOOLEAN DEFAULT false;
