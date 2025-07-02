-- liquibase formatted sql

-- changeset ivanov_n:1

ALTER TABLE users
    ADD COLUMN enabled BOOLEAN NOT NULL