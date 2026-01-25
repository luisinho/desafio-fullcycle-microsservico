CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id_from VARCHAR(255) NOT NULL UNIQUE,
    account_id_to VARCHAR(255) NOT NULL UNIQUE,    
    balance_account_from DECIMAL(15,2) NOT NULL,
    balance_account_to DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);