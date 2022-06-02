CREATE SCHEMA IF NOT EXISTS scribbler_schema AUTHORIZATION sa;

CREATE TABLE IF NOT EXISTS scribbler_schema.table_chunks (
 id IDENTITY,
 file VARCHAR NOT NULL,
 chunk VARCHAR NOT NULL
);
