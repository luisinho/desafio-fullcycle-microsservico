package database

import (
	"database/sql"
	"log"
	"os"
)

func RunMigrations(db *sql.DB) error {
	files, err := os.ReadDir("./internal/migrations")
	if err != nil {
		return err
	}

	for _, file := range files {
		log.Println("Running migration:", file.Name())

		content, err := os.ReadFile("./internal/migrations/" + file.Name())
		if err != nil {
			return err
		}

		_, err = db.Exec(string(content))
		if err != nil {
			return err
		}
	}

	return nil
}
