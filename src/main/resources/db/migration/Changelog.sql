-- liquibase formatted sql
-- changeset users:1
CREATE TABLE "Users"
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 ),
    "Login" character varying(50),
    "Password" character varying(80),
    "IsAdmin" boolean default false,
    PRIMARY KEY (id)
);
create table "Quotations"
(
    "Id"       integer generated always as identity
        constraint "Id"
            primary key,
    "CharCode" varchar
        constraint "UniqueCharCodes"
            unique,
    "NumCode"  varchar,
    "Name"     varchar,
    "Value"    double precision,
    "Previous" double precision,
    "Date"     timestamp
);

alter table "Quotations"
    owner to postgres;
-- rollback DROP TABLE "Quotations"


