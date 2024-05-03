-- run these commands before starting the project, in order to setup the database and the connecting user
CREATE USER orders_admin WITH PASSWORD 'orders_pass';

CREATE DATABASE orders;
GRANT ALL PRIVILEGES ON DATABASE orders TO orders_admin;