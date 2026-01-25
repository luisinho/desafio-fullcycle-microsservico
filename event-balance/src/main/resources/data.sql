-- Base de dados H2 (dev) / MySQL (prod)
-- Criação da tabela accounts

CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id_from VARCHAR(255) NOT NULL UNIQUE,
    account_id_to VARCHAR(255) NOT NULL UNIQUE,    
    balance_account_from DECIMAL(15,2) NOT NULL,
    balance_account_to DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DELETE FROM accounts;

-- Inserts fictícios iniciais (dev)

INSERT INTO accounts (account_id_from, account_id_to, balance_account_from, balance_account_to)
VALUES ('acc-from-1', 'acc-to-1', 150.00, 850.00);

INSERT INTO accounts (account_id_from, account_id_to, balance_account_from, balance_account_to)
VALUES ('acc-from-2', 'acc-to-2', 200.00, 800.00);

INSERT INTO accounts (account_id_from, account_id_to, balance_account_from, balance_account_to)
VALUES ('acc-from-3', 'acc-to-3', 200.00, 1000.00);