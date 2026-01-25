package database

import (
	"database/sql"
	"testing"

	"github.com.br/devfullcycle/fc-ms-wallet/internal/entity"
	"github.com/stretchr/testify/suite"
)

type TransactionDBTestSuite struct {
	suite.Suite
	db            *sql.DB
	clientFrom    *entity.Client
	clientTo      *entity.Client
	accountFrom   *entity.Account
	accountTo     *entity.Account
	transactionDB *TransactionDB
}

func (s *TransactionDBTestSuite) SetupSuite() {
	db, err := sql.Open("sqlite3", ":memory:")
	s.Nil(err)
	s.db = db

	createTableClientsSQL := `
	CREATE TABLE clients (
		id varchar(255) PRIMARY KEY,
		name varchar(255),
		email varchar(255),
		created_at date
	);`

	createTableAccountsSQL := `
	CREATE TABLE accounts (
		id varchar(255) PRIMARY KEY,
		client_id varchar(255),
		balance float,
		created_at date
	);`

	createTableTransactionsSQL := `
	CREATE TABLE transactions (
		id varchar(255) PRIMARY KEY,
		account_id_from varchar(255),
		account_id_to varchar(255),
		amount float,
		created_at date
	);`

	db.Exec(createTableClientsSQL)
	db.Exec(createTableAccountsSQL)
	db.Exec(createTableTransactionsSQL)

	client1, err := entity.NewClient("John1", "J@j1.com")
	s.Nil(err)
	s.clientFrom = client1

	client2, err := entity.NewClient("John2", "J@j2.com")
	s.Nil(err)
	s.clientTo = client2

	accountFrom := entity.NewAccount(s.clientFrom)
	accountFrom.Balance = 1000.0
	s.accountFrom = accountFrom

	accountTo := entity.NewAccount(s.clientTo)
	accountTo.Balance = 1000.0
	s.accountTo = accountTo

	s.transactionDB = NewTransactionDB(db)
}

func (s *TransactionDBTestSuite) TearDownSuite() {
	defer s.db.Close()
	s.db.Exec("DROP TABLE clients")
	s.db.Exec("DROP TABLE accounts")
	s.db.Exec("DROP TABLE transactions")
}

func TestTransactionDBTestSuite(t *testing.T) {
	suite.Run(t, new(TransactionDBTestSuite))
}

func (s *TransactionDBTestSuite) TestCreate() {
	transaction, err := entity.NewTransaction(s.accountFrom, s.accountTo, 100)
	s.Nil(err)
	err = s.transactionDB.Create(transaction)
	s.Nil(err)
}
