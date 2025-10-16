CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE
);
<<<<<<< HEAD
CREATE TABLE budgets (
    budget_id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    monthly_income NUMERIC(13, 2) NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE budget_categories (
    budget_category_id UUID PRIMARY KEY,
    budget_id UUID NOT NULL,
    percentage NUMERIC(5, 4) NOT NULL,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    CONSTRAINT fk_budget
        FOREIGN KEY(budget_id)
        REFERENCES budgets(budget_id)
        ON DELETE CASCADE
);

CREATE TABLE fixed_costs (
    fixed_cost_id UUID PRIMARY KEY,
    amount NUMERIC(13, 2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    budget_category_id UUID,
    CONSTRAINT fk_budget_category
        FOREIGN KEY(budget_category_id)
        REFERENCES budget_categories(budget_category_id)
        ON DELETE CASCADE
);
=======

>>>>>>> 654e0df7a4f59ae7860e88e2655b32ae3851d784
