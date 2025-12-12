-- Создание базы данных
CREATE DATABASE user_service_db;

-- Проверка существования пользователя и его создание
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_roles WHERE rolname = 'user_user'
    ) THEN
        CREATE USER user_user WITH PASSWORD 'user_password';
    END IF;
END
$$;

-- Предоставление прав пользователю
GRANT CONNECT ON DATABASE user_service_db TO user_user;
GRANT USAGE ON SCHEMA public TO user_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO user_user;