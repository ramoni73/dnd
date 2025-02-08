# Базовый образ PostgreSQL
FROM postgres:15

# Установка переменных окружения
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=postgres

# Копирование SQL-скриптов в директорию инициализации PostgreSQL
COPY init-scripts/ /docker-entrypoint-initdb.d/

# Порт PostgreSQL
EXPOSE 5432