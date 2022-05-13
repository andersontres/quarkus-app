--liquibase formatted sql

--changeset Anderson:01022022-001

CREATE TABLE car (
   id UUID NOT NULL,
   name VARCHAR(100) NOT NULL,
   CONSTRAINT car_pk PRIMARY KEY (id)
)
-- rollback DROP TABLE car