-- Создание базы данных
CREATE DATABASE reference_service_db;

-- Проверка существования пользователя и его создание
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_roles WHERE rolname = 'reference_user'
    ) THEN
        CREATE USER reference_user WITH PASSWORD 'reference_password';
    END IF;
END
$$;

-- Предоставление прав пользователю
GRANT CONNECT ON DATABASE reference_service_db TO reference_user;
GRANT USAGE ON SCHEMA public TO reference_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO reference_user;