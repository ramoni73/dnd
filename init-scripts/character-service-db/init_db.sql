CREATE DATABASE character_service_db;

CREATE USER character_user WITH PASSWORD 'character_user';

GRANT CONNECT ON DATABASE character_service_db TO character_user;
GRANT USAGE ON SCHEMA public TO character_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO character_user;