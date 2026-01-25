CREATE TABLE IF NOT EXISTS accounts (
  id VARCHAR(255) PRIMARY KEY,
  client_id VARCHAR(255),
  balance FLOAT,
  created_at DATETIME
);