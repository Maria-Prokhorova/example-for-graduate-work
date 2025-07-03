-- liquibase formatted sql

-- changeset ivanov_n:2

ALTER TABLE users
    ALTER COLUMN password TYPE VARCHAR(128)