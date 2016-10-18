CREATE DATABASE skeleton;
-- this won't work in cockroachdb
CREATE USER fancyuser WITH PASSWORD 'fancypassword';
GRANT ALL ON DATABASE skeleton TO fancyuser;

CREATE TABLE skeleton.SWANSON_QUOTE (
  ID INT,
  QUOTE VARCHAR(50) NOT NULL
);