CREATE DATABASE reference_service_db;

CREATE USER reference_user WITH PASSWORD 'reference_pass';

GRANT CONNECT ON DATABASE reference_service_db TO reference_user;
GRANT USAGE ON SCHEMA public TO reference_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO reference_user;